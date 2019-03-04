package hu.robnn.reportserver.model.dto

import hu.robnn.commons.interfaces.UuidHolder
import java.util.*

data class ConnectionDescriptor(var uuid: UUID = UUID.randomUUID(),
                                var driverUuid: UUID? = null,
                                var jdbcConnectionString: String? = null,
                                var username: String? = null,
                                var password: String? = null): UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = UUID.fromString(p0!!)
        }
    }

    override fun getUuid(): String {
        return uuid.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConnectionDescriptor

        if (uuid != other.uuid) return false
        if (driverUuid != other.driverUuid) return false
        if (jdbcConnectionString != other.jdbcConnectionString) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + (driverUuid?.hashCode() ?: 0)
        result = 31 * result + (jdbcConnectionString?.hashCode() ?: 0)
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }


}

data class ConnectionDescriptorsResponse(var connections: List<ConnectionDescriptor> = mutableListOf())