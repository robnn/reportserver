package hu.robnn.reportserver.enums

import hu.robnn.reportserver.model.dmo.query.HQueryScheduleData
import java.util.*

enum class ScheduledExecutionType {
    ONE_TIME,
    DAILY,
    WEEKLY,
    ;

    fun shouldExecute(scheduleData: HQueryScheduleData?): Boolean {
        if (scheduleData == null) {
            return false
        }
        return when(this) {
            ONE_TIME -> Date().after(scheduleData.date)
            DAILY -> calcDailyExecution(scheduleData.timeInDay)
            WEEKLY -> calcWeeklyExecution(scheduleData.timeInDay, scheduleData.day)
        }
    }

    private fun calcWeeklyExecution(timeOfDay: String?, day: Day?): Boolean {
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return day?.number == dayOfWeek && calcDailyExecution(timeOfDay)
    }

    private fun calcDailyExecution(timeOfDay: String?): Boolean {
        return if (timeOfDay == null) {
            false
        } else {
            val time = TimeOfDay.parseFromString(timeOfDay)
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)
            return hour == time.hour && minutes == time.minute
        }
    }

    class TimeOfDay(val hour: Int,
                    val minute: Int) {
        companion object {
            fun parseFromString(string: String): TimeOfDay {
                val split = string.split(":")
                return TimeOfDay(split[0].toInt(), split[1].toInt())
            }
        }

    }
}