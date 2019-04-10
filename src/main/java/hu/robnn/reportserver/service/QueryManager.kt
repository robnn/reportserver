package hu.robnn.reportserver.service

import hu.robnn.reportserver.converter.Converter
import hu.robnn.reportserver.enums.QueryErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.model.dto.PagedQueryResponse
import hu.robnn.reportserver.model.dto.ParametrizedQueryRequest
import hu.robnn.reportserver.service.queryhelper.NamedParameterStatement
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.lang.Exception
import java.sql.ResultSet
import java.util.*

interface QueryManager {
    fun executeQuery(connectionUuid: UUID, query: String): ResultSet
    fun executeTestQuery(connectionUuid: UUID): Boolean
    fun executePaginatedQuery(parametrizedQueryRequest: ParametrizedQueryRequest): PagedQueryResponse
    fun executeQueryWithNamedParameters(connectionUuid: UUID, query: String, parameters: Map<String, Any>): ResultSet
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

    override fun executePaginatedQuery(parametrizedQueryRequest: ParametrizedQueryRequest): PagedQueryResponse {

        if (checkIfParametrized(parametrizedQueryRequest)
                && parametrizedQueryRequest.connectionUuid != null && parametrizedQueryRequest.queryString != null) {
            val resultSet = executeQueryWithNamedParameters(parametrizedQueryRequest.connectionUuid!!,
                    parametrizedQueryRequest.queryString!!, parametrizedQueryRequest.parameters!!)
            return Converter.convertToPagedQueryResult(resultSet, parametrizedQueryRequest)
        }

        if (parametrizedQueryRequest.connectionUuid != null && parametrizedQueryRequest.queryString != null) {
            val resultSet = executeQuery(parametrizedQueryRequest.connectionUuid!!, parametrizedQueryRequest.queryString!!)
            return Converter.convertToPagedQueryResult(resultSet, parametrizedQueryRequest)
        } else {
            throw ReportServerMappedException(QueryErrorCause.QUERY_AND_UUID_MUST_NOT_BE_NULL)
        }
    }

    private fun checkIfParametrized(parametrizedQueryRequest: ParametrizedQueryRequest) =
        parametrizedQueryRequest.queryString != null && !NamedParameterStatement.extractParams(parametrizedQueryRequest.queryString!!).isEmpty()

    override fun executeQueryWithNamedParameters(connectionUuid: UUID, query: String, parameters: Map<String, Any>): ResultSet {
        val connectionForConnectionDescriptorUuid = connectionManager.getConnectionForConnectionDescriptorUuid(connectionUuid)

        val namedParameterStatement = NamedParameterStatement(connectionForConnectionDescriptorUuid, query, parameters)
        return namedParameterStatement.executeQuery()

    }
}