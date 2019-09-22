package hu.robnn.reportserver.model.dmo

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_connection_descriptor", schema = "public")
class HConnectionDescriptor : UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_driver_seq")
    @SequenceGenerator(name = "rs_driver_seq", sequenceName = "rs_driver_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @ManyToOne
    @JoinColumn(name = "rs_driver_id")
    var driver: HDriver? = null

    @Column
    var host: String? = null

    @Column
    var port: String? = null

    @Column
    var dbName: String? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

    @Transient
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HConnectionDescriptor

        if (id != other.id) return false
        if (uuid != other.uuid) return false
        if (driver!!.id != other.driver!!.id) return false
        if (host != other.host) return false
        if (port != other.port) return false
        if (dbName != other.dbName) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    @Transient
    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + uuid.hashCode()
        result = 31 * result + (driver!!.id?.hashCode() ?: 0)
        result = 31 * result + (host?.hashCode() ?: 0)
        result = 31 * result + (port?.hashCode() ?: 0)
        result = 31 * result + (dbName?.hashCode() ?: 0)
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }

}