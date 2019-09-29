package hu.robnn.reportserver.model.dmo.query

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_query_chart", schema = "public")
open class HQueryChart: UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_query_chart_seq")
    @SequenceGenerator(name = "rs_query_chart_seq", sequenceName = "rs_query_chart_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @Column(name = "chart_type")
    open var chartType: String? = null

    @OneToOne(targetEntity = HQueryColumn::class)
    @JoinColumn(name = "label_column_id")
    open var labelColumn: HQueryColumn? = null

    @OneToOne(targetEntity = HQueryColumn::class)
    @JoinColumn(name = "data_column_id")
    open var dataColumn: HQueryColumn? = null

    @ManyToOne(targetEntity = HQuery::class)
    @JoinColumn(name = "query_id")
    open var query: HQuery? = null
}
