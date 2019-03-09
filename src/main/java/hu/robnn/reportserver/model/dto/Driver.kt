package hu.robnn.reportserver.model.dto

import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.enums.DbType
import java.util.*

data class Driver(var uuid: UUID = UUID.randomUUID(),
                  var name: String? = null,
                  var driverClassName: String? = null,
                  var dbType: DbType? = null): UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = UUID.fromString(p0!!)
        }
    }

    override fun getUuid(): String {
        return uuid.toString()
    }
}

data class Drivers(val drivers: List<Driver?> = mutableListOf())