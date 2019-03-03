package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.DriverRepository
import hu.robnn.reportserver.mapper.DriverMapper
import hu.robnn.reportserver.model.dmo.HDriver
import hu.robnn.reportserver.model.dto.Driver
import hu.robnn.reportserver.model.dto.Drivers
import hu.robnn.reportserver.model.DriverShim
import hu.robnn.reportserver.model.dto.ConnectionDescriptor
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URL
import java.sql.Connection
import java.sql.DriverManager
import java.net.URLClassLoader
import java.util.jar.JarFile


interface DriverService {
    fun listInstalledDrivers(): Drivers
    fun installDriver(multipartFile: MultipartFile): Driver?
    fun createAndStoreConnection(connectionDescriptor: ConnectionDescriptor)
}

@Component
class DriverServiceImpl(private val driverMapper: DriverMapper,
                        private val driverRepository: DriverRepository,
                        private val jdbcFolder: File) : DriverService {

    companion object {
        val connectionMap = HashMap<ConnectionDescriptor, Connection>()
    }

    override fun listInstalledDrivers(): Drivers =
            Drivers(driverRepository.findAll().map { driverMapper.map(it) })


    override fun installDriver(multipartFile: MultipartFile): Driver? {
        multipartFile.transferTo(File(jdbcFolder.absolutePath, multipartFile.originalFilename))
        val driverName = multipartFile.originalFilename
        val hDriver = HDriver()
        hDriver.name = driverName
        return driverMapper.map(driverRepository.save(hDriver))
    }

    override fun createAndStoreConnection(connectionDescriptor: ConnectionDescriptor) {
        val driverInRepository = driverRepository.findByUuid(connectionDescriptor.driverUuid.toString())
        val driverPath = jdbcFolder.absolutePath + "/" + driverInRepository.name
        val driverUrl = URL("jar:file:$driverPath!/")
        val ucl = URLClassLoader(arrayOf(driverUrl))
        var neededClassName = findDriverClassName(driverPath, ucl)


        val d = Class.forName(neededClassName, true, ucl).newInstance() as java.sql.Driver
        DriverManager.registerDriver(DriverShim(d))
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost/reportserver", "postgres", "postgres")
        // Success!

        return connection
    }

    private fun findDriverClassName(driverPath: String, ucl: URLClassLoader): String {
        val driverFile = File(driverPath)
        val jarFile = JarFile(driverFile)

        var neededClassName = ""
        for (entry in jarFile.entries()) {
            val className = entry.name.replace("/", ".")
            if (className.contains(".class") && !className.contains("$")) {
                val forName = Class.forName(className.replace(".class", ""), true, ucl)
                if (!forName.isInterface && java.sql.Driver::class.java.isAssignableFrom(forName)) {
                    neededClassName = className.replace(".class", "")
                    break
                }
            }
        }
        return neededClassName
    }
}