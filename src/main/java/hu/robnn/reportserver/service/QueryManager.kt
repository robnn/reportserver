package hu.robnn.reportserver.service

import hu.robnn.reportserver.converter.Converter
import hu.robnn.reportserver.dao.QueryRepository
import hu.robnn.reportserver.enums.QueryErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.mapper.QueryMapper
import hu.robnn.reportserver.model.dmo.HQueryColumn
import hu.robnn.reportserver.model.dto.*
import hu.robnn.reportserver.service.queryhelper.NamedParameterStatement
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import java.lang.Exception
import java.sql.ResultSet
import java.util.*

interface QueryManager {
    fun executeQuery(connectionUuid: UUID, query: String): ResultSet
    fun executeQuery(request: NotPagedParametrizedQueryRequest): QueryResponse
    fun executeTestQuery(connectionUuid: UUID): Boolean
    fun executePaginatedQuery(parametrizedQueryRequest: ParametrizedQueryRequest): PagedQueryResponse
    fun executeQueryWithNamedParameters(connectionUuid: UUID, query: String, parameters: Map<String, Any>): ResultSet
    fun listQueries(): QueryRequests
}

@Component
class QueryManagerImpl(@Lazy private val connectionManager: ConnectionManager,
                       private val queryMapper: QueryMapper,
                       private val queryRepository: QueryRepository) : QueryManager {

    override fun executeQuery(connectionUuid: UUID, query: String) : ResultSet {
        val connectionForConnectionDescriptorUuid = connectionManager.getConnectionForConnectionDescriptorUuid(connectionUuid)
        try {
            val createStatement = connectionForConnectionDescriptorUuid.createStatement()
            return createStatement.executeQuery(query)
        } catch (e: Exception) {
            throw ReportServerMappedException(e.message!!)
        }
    }

    override fun executeQuery(request: NotPagedParametrizedQueryRequest): QueryResponse {
        if (checkIfParametrized(request)
                && request.connectionUuid != null && request.queryString != null) {
            val resultSet = executeQueryWithNamedParameters(request.connectionUuid!!,
                    request.queryString!!, request.parameters)
            return Converter.convertToQueryResult(resultSet)
        }

        if (request.connectionUuid != null && request.queryString != null) {
            val resultSet = executeQuery(request.connectionUuid!!, request.queryString!!)
            return Converter.convertToQueryResult(resultSet)
        } else {
            throw ReportServerMappedException(QueryErrorCause.QUERY_AND_UUID_MUST_NOT_BE_NULL)
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
                    parametrizedQueryRequest.queryString!!, parametrizedQueryRequest.parameters)
            val columns = Converter.getColumns(resultSet)
            if (parametrizedQueryRequest.queryName != null) {
                saveQuery(parametrizedQueryRequest, columns)
            }
            return Converter.convertToPagedQueryResult(resultSet, parametrizedQueryRequest, queryMapper.mapToColumns(columns))
        }

        if (parametrizedQueryRequest.connectionUuid != null && parametrizedQueryRequest.queryString != null) {
            val resultSet = executeQuery(parametrizedQueryRequest.connectionUuid!!, parametrizedQueryRequest.queryString!!)
            val columns = Converter.getColumns(resultSet)
            if (parametrizedQueryRequest.queryName != null) {
                saveQuery(parametrizedQueryRequest, columns)
            }
            return Converter.convertToPagedQueryResult(resultSet, parametrizedQueryRequest, queryMapper.mapToColumns(columns))
        } else {
            throw ReportServerMappedException(QueryErrorCause.QUERY_AND_UUID_MUST_NOT_BE_NULL)
        }
    }

    private fun saveQuery(parametrizedQueryRequest: ParametrizedQueryRequest, columns: Set<HQueryColumn>) {
        queryRepository.save(queryMapper.mapToQuery(parametrizedQueryRequest, queryRepository.findByQueryName(parametrizedQueryRequest.queryName),
                columns))
    }

    private fun checkIfParametrized(parametrizedQueryRequest: ParametrizedQueryRequest) =
        parametrizedQueryRequest.queryString != null && !NamedParameterStatement.extractParams(parametrizedQueryRequest.queryString!!).isEmpty()

    private fun checkIfParametrized(parametrizedQueryRequest: NotPagedParametrizedQueryRequest) =
            parametrizedQueryRequest.queryString != null && !NamedParameterStatement.extractParams(parametrizedQueryRequest.queryString!!).isEmpty()

    override fun executeQueryWithNamedParameters(connectionUuid: UUID, query: String, parameters: Map<String, Any>): ResultSet {
        val connectionForConnectionDescriptorUuid = connectionManager.getConnectionForConnectionDescriptorUuid(connectionUuid)

        val namedParameterStatement = NamedParameterStatement(connectionForConnectionDescriptorUuid, query, parameters)
        return namedParameterStatement.executeQuery()
    }

    override fun listQueries(): QueryRequests {
        return QueryRequests(queryRepository.findAll().map { queryMapper.mapToRequest(it) }.toSet())
    }
}