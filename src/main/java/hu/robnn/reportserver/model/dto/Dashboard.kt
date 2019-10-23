package hu.robnn.reportserver.model.dto

import java.util.*

class DashboardQuery(var uuid: UUID = UUID.randomUUID(),
                     var queryUuid: UUID? = null,
                     var isChart: Boolean? = null,
                     var order: Int? = null)

class Dashboard(var uuid: UUID = UUID.randomUUID(),
                var dashboardQueries: Set<DashboardQuery> = mutableSetOf(),
                var userName: String? = null)