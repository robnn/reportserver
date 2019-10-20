package hu.robnn.reportserver.model.dmo.query

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.model.dmo.HDashboard
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_dashboard_query", schema = "public")
open class HDashboardQuery : UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_dashboard_query_seq")
    @SequenceGenerator(name = "rs_dashboard_query_seq", sequenceName = "rs_dashboard_query_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @OneToOne(targetEntity = HQuery::class)
    @JoinColumn(name = "query_id")
    open var query: HQuery? = null

    @Column(name = "is_chart")
    open var isChart: Boolean? = null

    @OneToOne(targetEntity = HDashboard::class)
    @JoinColumn(name = "dashboard_id")
    open var dashboard: HDashboard? = null
}