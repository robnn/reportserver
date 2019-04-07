package hu.robnn.reportserver.service

import hu.robnn.reportserver.converter.Converter
import hu.robnn.reportserver.enums.QueryErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.model.dto.PagedQueryRequest
import hu.robnn.reportserver.model.dto.PagedQueryResponse
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.lang.Exception
import java.sql.ResultSet
import java.util.*

interface QueryManager {
    fun executeQuery(connectionUuid: UUID, query: String): ResultSet
    fun executeTestQuery(connectionUuid: UUID): Boolean
    fun executePaginatedQuery(pagedQueryRequest: PagedQueryRequest): PagedQueryResponse
}

@Component
class QueryManagerImpl(@Lazy private val connectionManager: ConnectionManager) : QueryManager {

    override fun executeQuery(connectionUuid: UUID, query: String) : ResultSet {
        val connectionForConnectionDescriptorUuid = connectionManager.getConnectionForConnectionDescriptorUuid(connectionUuid)
        val createStatement = connectionForConnectionDescriptorUuid.createStatement()
        try {
            val executeQuery = createStatement.executeQuery(query)
            return executeQuery
        } catch (e: Exception) {
            throw ReportServerMappedException(e.message!!)
        }
    }

    override fun executeTestQuery(connectionUuid: UUID): Boolean {
        try {
            executeQuery(connectionUuid, "SELECT 1")
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun executePaginatedQuery(pagedQueryRequest: PagedQueryRequest): PagedQueryResponse {
        if (pagedQueryRequest.connectionUuid != null && pagedQueryRequest.queryString != null) {
            val resultSet = executeQuery(pagedQueryRequest.connectionUuid!!, pagedQueryRequest.queryString!!)
            return Converter.convertToPagedQueryResult(resultSet, pagedQueryRequest)
        } else {
            throw ReportServerMappedException(QueryErrorCause.QUERY_AND_UUID_MUST_NOT_BE_NULL)
        }
    }
}