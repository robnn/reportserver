package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.ConnectionDescriptorRepository
import hu.robnn.reportserver.mapper.ConnectionDescriptorMapper
import hu.robnn.reportserver.model.DriverShim
import hu.robnn.reportserver.model.dmo.HConnectionDescriptor
import hu.robnn.reportserver.model.dmo.HDriver
import hu.robnn.reportserver.model.dto.ConnectionDescriptor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.RuntimeException
import java.net.URL
import java.net.URLClassLoader
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import kotlin.collections.HashMap

interface ConnectionManager {
    fun initializeConnections()
    fun createConnection(connectionDescriptor: ConnectionDescriptor): ConnectionDescriptor
    fun getConnectionForConnectionDescriptorUuid(uuid: UUID): Connection
    fun listConnections(): List<ConnectionDescriptor>
}

@Component
open class ConnectionManagerImpl(private val connectionDescriptorRepository: ConnectionDescriptorRepository,
                                 private val connectionDescriptorMapper: ConnectionDescriptorMapper,
                                 private val jdbcFolder: File,
                                 private val driverService: DriverService,
                                 private val queryManager: QueryManager) : ConnectionManager {

    private val connections: MutableMap<HConnectionDescriptor, Connection> = HashMap()

    init {
        initializeConnections()
    }

    final override fun initializeConnections() {
        connectionDescriptorRepository.findAll().forEach {
            if(it?.host != null && it.port != null && it.dbName != null && it.driver?.dbType != null) {
                registerDriverToDriverManager(it.driver!!)
                val connection = DriverManager.getConnection(it.driver?.dbType?.buildJdbcString(it.host!!, it.port!!, it.dbName!!), it.username, it.password)
                connections[it] = connection
            }
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNNECESSARY_SAFE_CALL")
    @Transactional
    override fun createConnection(connectionDescriptor: ConnectionDescriptor): ConnectionDescriptor {
        val saved = connectionDescriptorRepository.save(connectionDescriptorMapper.map(connectionDescriptor, null))
        saved?.let {
            try {
                val connection = DriverManager.getConnection(it.driver?.dbType?.buildJdbcString(it.host!!, it.port!!, it.dbName!!), it.username, it.password)
                connections.putIfAbsent(it, connection)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        return connectionDescriptorMapper.map(saved)!!
    }

    override fun getConnectionForConnectionDescriptorUuid(uuid: UUID): Connection {
        val connection = connections[connectionDescriptorRepository.findByUuid(uuid.toString())]
        if (connection != null) {
            return connection
        } else {
            throw IllegalStateException("No connection for descriptor!")
        }
    }

    override fun listConnections(): List<ConnectionDescriptor> {
        val connections = connectionDescriptorRepository.findAll().map { connectionDescriptorMapper.map(it)!! }
        connections.forEach {it.isAlive = queryManager.executeTestQuery(it.uuid) }
        return connections
    }

    private fun registerDriverToDriverManager(driverInRepository: HDriver) {
        val driverPath = jdbcFolder.absolutePath + "/" + driverInRepository.name
        val driverUrl = URL("jar:file:$driverPath!/")
        val ucl = URLClassLoader(arrayOf(driverUrl))
        val neededClassName = driverService.findDriverClassName(driverPath, ucl)

        val d = Class.forName(neededClassName, true, ucl).newInstance() as java.sql.Driver
        DriverManager.registerDriver(DriverShim(d))
    }



}