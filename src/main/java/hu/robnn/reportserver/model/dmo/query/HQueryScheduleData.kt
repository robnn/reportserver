package hu.robnn.reportserver.model.dmo.query

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.commons.interfaces.UuidHolder
import hu.robnn.reportserver.enums.Day
import hu.robnn.reportserver.enums.ScheduledExecutionType
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_query_schedule_data", schema = "public")
open class HQueryScheduleData : UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_query_schedule_data_seq")
    @SequenceGenerator(name = "rs_query_schedule_data_seq", sequenceName = "rs_query_schedule_data_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    @OneToOne(targetEntity = HQuery::class)
    @JoinColumn(name = "query_id")
    open var query: HQuery? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "scheduled_execution_type")
    open var scheduledExecutionType: ScheduledExecutionType? = null

    /**
     * Time in the day, for daily and weekly schedule, eg.: 11:23 or 23:59
     */
    @Column(name = "time_in_day")
    open var timeInDay: String? = null

    /**
     * Day for the weekly execution
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    open var day: Day? = null

    /**
     * Date for one time execution
     */
    @Column(name = "date")
    open var date: Date? = null
}