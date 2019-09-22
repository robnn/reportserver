package hu.robnn.reportserver.model.dto

import java.util.*

class QueryResponse(var result: MutableList<MutableMap<String, Any>> = mutableListOf(),
                    var columns: MutableList<Column> = mutableListOf())

class PagedQueryResponse(var pagedResult: MutableList<MutableMap<String, Any>> = mutableListOf(),
                         var totalNumberOfPages: Int? = null,
                         var actualPage: Int? = null,
                         var itemsPerPage: Int? = null,
                         var totalItems: Int? = null,
                         var columns: MutableList<Column> = mutableListOf())

open class PagedQueryRequest(var queryString: String? = null,
                             var connectionUuid: UUID? = null,
                             var itemsPerPage: Int? = null,
                             var neededPage: Int? = null,
                             var queryName: String? = null)

open class NotPagedParametrizedQueryRequest(var queryString: String? = null,
                                            var connectionUuid: UUID? = null,
                                            var parameters: Map<String, Any> = mutableMapOf())

class ParametrizedQueryRequest(var parameters: Map<String, Any> = mutableMapOf(),
                               var columns: MutableList<Column> = mutableListOf()) : PagedQueryRequest()

class QueryRequests(var queries: Set<ParametrizedQueryRequest> = mutableSetOf())

class Column(var columnName: String? = null,
             var columnType: String? = null)