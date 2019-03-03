package hu.robnn.reportserver.model.dmo

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_connection_descriptor")
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

    @Column(name = "jdbc_connection_string")
    var jdbcConnectionString: String? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

}