package hu.robnn.reportserver.mapper

import hu.robnn.reportserver.model.dmo.HDriver
import hu.robnn.reportserver.model.dto.Driver
import org.springframework.stereotype.Component
import java.util.*

@Component
class DriverMapper {
    fun map(source: HDriver?) : Driver? {
        if (source == null)
            return null
        val target = Driver()
        target.name = source.name
        target.uuid = UUID.fromString(source.uuid)
        return target
    }

    fun map(source: Driver?, target: HDriver?): HDriver? {
        if(source == null){
            return null
        }
        var realTarget = target
        if(realTarget == null) {
            realTarget = HDriver()
        }
        realTarget.name = source.name
        realTarget.uuid = source.uuid.toString()
        return realTarget
    }
}