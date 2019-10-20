package hu.robnn.reportserver.mapper

import hu.robnn.auth.dao.UserDao
import hu.robnn.reportserver.dao.QueryRepository
import hu.robnn.reportserver.enums.DashboardErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.model.dmo.HDashboard
import hu.robnn.reportserver.model.dmo.query.HDashboardQuery
import hu.robnn.reportserver.model.dto.Dashboard
import hu.robnn.reportserver.model.dto.DashboardQuery
import org.springframework.stereotype.Component
import java.util.*

@Component
class DashboardMapper(private val userDao: UserDao,
                      private val queryRepository: QueryRepository) {
    fun map(source: Dashboard, target: HDashboard?): HDashboard {
        var realTarget = target
        if (realTarget == null) {
            realTarget = HDashboard()
        }

        realTarget.user = userDao.findByUsername(source.userName)
                .orElseThrow { ReportServerMappedException(DashboardErrorCause.USER_NOT_FOUND) }
        realTarget.uuid = source.uuid.toString()
        realTarget.dashboardQueries = map(source.dashboardQueries, realTarget)
        return realTarget
    }

    fun map(source: Set<DashboardQuery>, dashboard: HDashboard): Set<HDashboardQuery> {
        return source.map { map(it, dashboard) }.toMutableSet()
    }

    fun map(source: DashboardQuery, dashboard: HDashboard): HDashboardQuery {
        val target = HDashboardQuery()
        target.dashboard = dashboard
        target.isChart = source.isChart
        target.query = queryRepository.findByUuid(source.queryUuid.toString())
        return target
    }

    fun map(source: HDashboard): Dashboard {
        val target = Dashboard()
        target.dashboardQueries = map(source.dashboardQueries)
        target.userName = source.user?.username
        return target
    }

    private fun map(dashboardQueries: Set<HDashboardQuery>): Set<DashboardQuery> {
        return dashboardQueries.map { map(it) }.toMutableSet()
    }

    private fun map(source: HDashboardQuery): DashboardQuery {
        val target = DashboardQuery()
        target.isChart = source.isChart
        target.queryUuid = UUID.fromString(source.query?.uuid)
        target.uuid = UUID.fromString(source.uuid)
        return target
    }
}