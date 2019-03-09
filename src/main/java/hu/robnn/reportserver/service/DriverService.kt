package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.DriverRepository
import hu.robnn.reportserver.enums.DbType
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
    fun installDriver(multipartFile: MultipartFile, dbType: String): Driver?
    fun findDriverClassName(driverPath: String, ucl: URLClassLoader): String
}

@Component
class DriverServiceImpl(private val driverMapper: DriverMapper,
                        private val driverRepository: DriverRepository,
                        private val jdbcFolder: File) : DriverService {


    override fun listInstalledDrivers(): Drivers =
            Drivers(driverRepository.findAll().map { driverMapper.map(it) })


    override fun installDriver(multipartFile: MultipartFile, dbType: String): Driver? {
        multipartFile.transferTo(File(jdbcFolder.absolutePath, multipartFile.originalFilename))
        val driverName = multipartFile.originalFilename
        val driverPath = jdbcFolder.absolutePath + "/" + multipartFile.originalFilename
        val driverUrl = URL("jar:file:$driverPath!/")
        val ucl = URLClassLoader(arrayOf(driverUrl))
        val driverClassName = findDriverClassName(driverPath, ucl)
        val hDriver = HDriver()
        hDriver.name = driverName
        hDriver.dbType = DbType.valueOf(dbType)
        hDriver.driverClassNAme = driverClassName
        return driverMapper.map(driverRepository.save(hDriver))
    }

    override fun findDriverClassName(driverPath: String, ucl: URLClassLoader): String {
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