package hu.robnn.reportserver.model.dmo

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.auth.dao.model.User
import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.model.dmo.query.HDashboardQuery
import hu.robnn.reportserver.model.dmo.query.HQuery
import hu.robnn.reportserver.model.dmo.query.HQueryChart
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_dashboard", schema = "public")
class HDashboard: UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_dashboard_seq")
    @SequenceGenerator(name = "rs_dashboard_seq", sequenceName = "rs_dashboard_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @OneToMany(targetEntity = HDashboardQuery::class, mappedBy = "dashboard", cascade = [CascadeType.ALL], orphanRemoval = true)
    var dashboardQueries: Set<HDashboardQuery> = mutableSetOf();

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(name = "user_id")
    var user: User? = null
    
}