package hu.robnn.reportserver.model.dmo

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.enums.DbType
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_driver", schema = "public")
class HDriver: UuidHolder {
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

    var name: String? = null

    @Column(name = "driver_class_name")
    var driverClassNAme: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "db_type")
    var dbType: DbType? = null
}