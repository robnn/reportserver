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
        target.uuid = UUID.fromString(source.uuid)
        target.driverUuid = UUID.fromString(source.driver!!.uuid)
        target.driverType = source.driver!!.dbType
        target.host = source.host
        target.port = source.port
        target.dbName = source.dbName
        target.password = source.password
        target.username = source.username
        return target
    }

    fun map(source: ConnectionDescriptor?, target: HConnectionDescriptor?): HConnectionDescriptor? {
        if (source == null)
            return null
        var realTarget = target
        if (realTarget == null){
            realTarget = HConnectionDescriptor()
        }
        realTarget.driver = driverRepository.findByUuid(source.driverUuid.toString())
        realTarget.host = source.host
        realTarget.port = source.port
        realTarget.dbName = source.dbName
        realTarget.password = source.password
        realTarget.username = source.username
        return realTarget
    }
}