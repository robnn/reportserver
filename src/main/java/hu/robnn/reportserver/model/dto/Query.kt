package hu.robnn.reportserver.model.dto

import hu.robnn.reportserver.enums.QueryVisibility
import java.util.*

class QueryResponse(var result: MutableList<MutableMap<String, Any>> = mutableListOf(),
                    var columns: MutableList<Column> = mutableListOf())

class PagedQueryResponse(var pagedResult: MutableList<MutableMap<String, Any>> = mutableListOf(),
                         var totalNumberOfPages: Int? = null,
                         var actualPage: Int? = null,
                         var itemsPerPage: Int? = null,
                         var totalItems: Int? = null,
                         var columns: MutableList<Column> = mutableListOf(),
                         var visibility: QueryVisibility = QueryVisibility.PUBLIC,
                         var charts: MutableList<Chart> = mutableListOf())

open class PagedQueryRequest(var queryString: String? = null,
                             var connectionUuid: UUID? = null,
                             var itemsPerPage: Int? = null,
                             var neededPage: Int? = null,
                             var queryName: String? = null,
                             var creatorUsername: String? = null,
                             var visibility: QueryVisibility = QueryVisibility.PUBLIC,
                             var teamUuidsAndNames: MutableList<TeamUuidAndName> = mutableListOf(),
                             var charts: MutableList<Chart> = mutableListOf())

open class TeamUuidAndName(var uuid: UUID = UUID.randomUUID(),
                           var name: String? = null)

open class NotPagedParametrizedQueryRequest(var queryString: String? = null,
                                            var connectionUuid: UUID? = null,
                                            var parameters: Map<String, Any> = mutableMapOf())

class ParametrizedQueryRequest(var parameters: Map<String, Any> = mutableMapOf(),
                               var columns: MutableList<Column> = mutableListOf()) : PagedQueryRequest()

class QueryRequests(var queries: Set<ParametrizedQueryRequest> = mutableSetOf())

class Column(var columnName: String? = null,
             var columnType: String? = null)

class Chart(var chartType: String? = null,
            var labelColumn: String? = null,
            var dataColumn: String? = null)