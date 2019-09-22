package hu.robnn.reportserver.model.dmo

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
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

    @OneToMany(targetEntity = HQueryParameter::class, mappedBy = "query", cascade = [CascadeType.ALL])
    open var queryParameters: Set<HQueryParameter> = mutableSetOf()

    @OneToMany(targetEntity = HQueryColumn::class, mappedBy = "query", cascade = [CascadeType.ALL])
    open var queryColumns: Set<HQueryColumn> = mutableSetOf()

}