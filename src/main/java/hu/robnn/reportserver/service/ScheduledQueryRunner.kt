package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.QueryRepository
import hu.robnn.reportserver.enums.ScheduledExecutionType
import hu.robnn.reportserver.mapper.QueryMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class ScheduledQueryRunner(private val queryManager: QueryManager,
                           private val queryRepository: QueryRepository,
                           private val queryMapper: QueryMapper) {
    companion object {
        const val ONE_MINUTE = 60000L
        val LOGGER: Logger = getLogger(ScheduledQueryRunner::class.java)
    }

    @Scheduled(fixedRate = ONE_MINUTE)
    @Transactional
    open fun scheduledQueryRun() {
        val scheduledQueries = queryRepository.findAll().filter {
            it.queryScheduleData != null && it.queryScheduleData?.scheduledExecutionType != null &&
                    it.queryScheduleData?.scheduledExecutionType?.shouldExecute(it.queryScheduleData)!!
        }
        scheduledQueries.forEach {
            queryManager.executePaginatedQuery(queryMapper.mapToRequest(it).apply { saveExecution = true })
            if (it.queryScheduleData?.scheduledExecutionType == ScheduledExecutionType.ONE_TIME) {
                // if one time, the scheduling data should be removed
                it.queryScheduleData = null
                queryRepository.save(it)
            }
            LOGGER.info("Scheduled query run for ${it.queryName}")
        }
    }
}