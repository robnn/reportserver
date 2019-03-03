package hu.robnn.reportserver.mapper

import hu.robnn.reportserver.dao.DriverRepository
import hu.robnn.reportserver.model.dmo.HConnectionDescriptor
import hu.robnn.reportserver.model.dto.ConnectionDescriptor
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConnectionDescriptorMapper(private val driverRepository: DriverRepository) {

    fun map(source: HConnectionDescriptor?): ConnectionDescriptor? {
        if (source == null)
            return null
        val target = ConnectionDescriptor()
        target.driverUuid = UUID.fromString(source.driver!!.uuid)
        target.jdbcConnectionString = source.jdbcConnectionString
        target.password = source.password
        target.username = source.username
        return target
    }

    fun map(source: ConnectionDescriptor?, target: HConnectionDescriptor): HConnectionDescriptor? {
        if (source == null)
            return null
        var realTarget = target
        if (realTarget == null){
            realTarget = HConnectionDescriptor()
        }
        realTarget.driver = driverRepository.findByUuid(source.driverUuid.toString())
        realTarget.jdbcConnectionString = source.jdbcConnectionString
        realTarget.password = source.password
        realTarget.username = source.username
        return realTarget
    }
}