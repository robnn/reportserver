package hu.robnn.reportserver.service

import hu.robnn.reportserver.dao.DashboardRepository
import hu.robnn.reportserver.enums.DashboardErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.mapper.DashboardMapper
import hu.robnn.reportserver.model.dto.Dashboard
import org.springframework.stereotype.Component

interface DashboardService {
    fun manageDashboard(dashboard: Dashboard) : Dashboard
    fun findDashboardByUsername(username: String): Dashboard?
}

@Component
class DashboardServiceImpl(private val dashboardMapper: DashboardMapper,
                           private val dashboardRepository: DashboardRepository) : DashboardService {
    override fun manageDashboard(dashboard: Dashboard): Dashboard {
        correctDashboardOrder(dashboard)
        return dashboardMapper.map(dashboardRepository.save(
                dashboardMapper.map(dashboard, dashboardRepository.findByUuid(dashboard.uuid.toString()))))
    }

    private fun correctDashboardOrder(dashboard: Dashboard) {
        dashboard.dashboardQueries.forEachIndexed { index, dashboardQuery ->
            dashboardQuery.order = index
        }
    }

    override fun findDashboardByUsername(username: String): Dashboard? {
        return dashboardMapper.map(dashboardRepository.findByUsername(username)
                .orElseThrow { ReportServerMappedException(DashboardErrorCause.DASHBOARD_FOR_USER_NOT_FOUND) })
    }

}