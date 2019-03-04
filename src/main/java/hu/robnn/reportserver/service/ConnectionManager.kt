package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.ConnectionDescriptorRepository
import hu.robnn.reportserver.mapper.ConnectionDescriptorMapper
import hu.robnn.reportserver.model.DriverShim
import hu.robnn.reportserver.model.dmo.HConnectionDescriptor
import hu.robnn.reportserver.model.dmo.HDriver
import hu.robnn.reportserver.model.dto.ConnectionDescriptor
import org.springframework.stereotype.Component
import java.io.File
import java.lang.IllegalStateException
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
}

@Component
class ConnectionManagerImpl(private val connectionDescriptorRepository: ConnectionDescriptorRepository,
                            private val connectionDescriptorMapper: ConnectionDescriptorMapper,
                            private val jdbcFolder: File,
                            private val driverService: DriverService): ConnectionManager {

    private val connections: MutableMap<HConnectionDescriptor, Connection> = HashMap()

    init {
        initializeConnections()
    }

    override fun initializeConnections() {
        connectionDescriptorRepository.findAll().forEach {
            registerDriverToDriverManager(it.driver!!)
            val connection = DriverManager.getConnection(it.jdbcConnectionString, it.username, it.password)
            connections[it] = connection
        }
    }

    override fun createConnection(connectionDescriptor: ConnectionDescriptor): ConnectionDescriptor {
        val saved = connectionDescriptorRepository.save(connectionDescriptorMapper.map(connectionDescriptor, null))
        saved?.let { connections.putIfAbsent(it, DriverManager.getConnection(it.jdbcConnectionString, it.username, it.password)) }
        return connectionDescriptorMapper.map(saved)!!
    }

    override fun getConnectionForConnectionDescriptorUuid(uuid: UUID): Connection {
        val connection = connections[connectionDescriptorRepository.findByUuid(uuid.toString())]
        if (connection!= null) {
            return connection
        } else {
            throw IllegalStateException("No connection for descriptor!")
        }
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