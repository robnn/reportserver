package hu.robnn.reportserver.model.dmo.query

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_query_column", schema = "public")
open class HQueryColumn: UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_query_column_seq")
    @SequenceGenerator(name = "rs_query_column_seq", sequenceName = "rs_query_column_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @Column(name = "column_name")
    open var columnName: String? = null

    @Column(name = "column_type")
    open var columnType: String? = null

    @ManyToOne(targetEntity = HQuery::class)
    @JoinColumn(name = "query_id")
    open var query: HQuery? = null
}
