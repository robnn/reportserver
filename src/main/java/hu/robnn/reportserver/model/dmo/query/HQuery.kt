package hu.robnn.reportserver.model.dmo.query

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.auth.dao.model.User
import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.enums.QueryVisibility
import hu.robnn.reportserver.model.dmo.team.HTeam
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_query", schema = "public")
open class HQuery: UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_query_seq")
    @SequenceGenerator(name = "rs_query_seq", sequenceName = "rs_query_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @Column(name = "query_string")
    open var queryString: String? = null

    @Column(name = "connection_uuid")
    open var connectionUuid: String? = null

    @Column(name = "query_name")
    open var queryName: String? = null

    @OneToMany(targetEntity = HQueryParameter::class, mappedBy = "query", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var queryParameters: MutableSet<HQueryParameter> = mutableSetOf()

    @OneToMany(targetEntity = HQueryColumn::class, mappedBy = "query", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var queryColumns: MutableSet<HQueryColumn> = mutableSetOf()

    @OneToMany(targetEntity = HQueryChart::class, mappedBy = "query", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var queryCharts: MutableSet<HQueryChart> = mutableSetOf()

    @OneToMany(targetEntity = HQueryExecution::class, mappedBy = "query", cascade = [CascadeType.ALL], orphanRemoval = true)
    open var queryExecutions: MutableSet<HQueryExecution> = mutableSetOf()

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    open var creatorUser: User? = null

    @Enumerated(value = EnumType.STRING)
    open var visibility: QueryVisibility = QueryVisibility.PUBLIC

    @ManyToMany(targetEntity = HTeam::class)
    @JoinTable(
            name = "rs_query_teams",
            joinColumns = [JoinColumn(name = "query_id")],
            inverseJoinColumns = [JoinColumn(name = "team_id")])
    open var teams: MutableSet<HTeam> = mutableSetOf()


}