package hu.robnn.reportserver.service

import hu.robnn.auth.service.UserContext
import hu.robnn.reportserver.converter.Converter
import hu.robnn.reportserver.dao.QueryRepository
import hu.robnn.reportserver.dao.TeamRepository
import hu.robnn.reportserver.enums.QueryErrorCause
import hu.robnn.reportserver.enums.QueryVisibility
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.mapper.QueryMapper
import hu.robnn.reportserver.model.dmo.query.HQuery
import hu.robnn.reportserver.model.dmo.query.HQueryColumn
import hu.robnn.reportserver.model.dto.*
import hu.robnn.reportserver.service.queryhelper.NamedParameterStatement
import org.apache.commons.lang3.StringUtils.isBlank
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
    fun getQueryColumns(parametrizedQueryRequest: ParametrizedQueryRequest): List<Column>
    fun saveQuery(parametrizedQueryRequest: ParametrizedQueryRequest): ParametrizedQueryRequest
}

@Component
class QueryManagerImpl(@Lazy private val connectionManager: ConnectionManager,
                       private val queryMapper: QueryMapper,
                       private val queryRepository: QueryRepository,
                       private val teamRepository: TeamRepository) : QueryManager {

    override fun saveQuery(parametrizedQueryRequest: ParametrizedQueryRequest): ParametrizedQueryRequest {
        if (isBlank(parametrizedQueryRequest.queryName)) {
            throw ReportServerMappedException(QueryErrorCause.NO_QUERY_NAME_SUPPLIED)
        }
        val resultSet = getResultSetFromRequest(parametrizedQueryRequest)
        val columns = Converter.getColumns(resultSet)
        return queryMapper.mapToRequest(saveQuery(parametrizedQueryRequest, columns))
    }

    override fun getQueryColumns(parametrizedQueryRequest: ParametrizedQueryRequest): List<Column> {
        val resultSet = getResultSetFromRequest(parametrizedQueryRequest)
        val columns = Converter.getColumns(resultSet)
        return queryMapper.mapToColumns(columns)
    }

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
        val resultSet = getResultSetFromRequest(parametrizedQueryRequest)
        val columns = Converter.getColumns(resultSet)
        return Converter.convertToPagedQueryResult(resultSet, parametrizedQueryRequest, queryMapper.mapToColumns(columns))
    }

    private fun getResultSetFromRequest(parametrizedQueryRequest: ParametrizedQueryRequest) : ResultSet {
        return if (checkIfParametrized(parametrizedQueryRequest)
                && parametrizedQueryRequest.connectionUuid != null && parametrizedQueryRequest.queryString != null) {
            executeQueryWithNamedParameters(parametrizedQueryRequest.connectionUuid!!,
                    parametrizedQueryRequest.queryString!!, parametrizedQueryRequest.parameters)
        } else if (parametrizedQueryRequest.connectionUuid != null && parametrizedQueryRequest.queryString != null) {
            executeQuery(parametrizedQueryRequest.connectionUuid!!, parametrizedQueryRequest.queryString!!)
        } else {
            throw ReportServerMappedException(QueryErrorCause.QUERY_AND_UUID_MUST_NOT_BE_NULL)
        }
    }

    private fun saveQuery(parametrizedQueryRequest: ParametrizedQueryRequest, columns: Set<HQueryColumn>): HQuery =
        queryRepository.save(queryMapper.mapToQuery(parametrizedQueryRequest,
                queryRepository.findByQueryName(parametrizedQueryRequest.queryName),
                columns))

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
        val username = UserContext.currentUser?.username
        val teams = teamRepository.findByUsername(username)
        return QueryRequests(queryRepository.findAll().map { queryMapper.mapToRequest(it) }
                .filter { query -> (query.visibility == QueryVisibility.TEAM &&
                                    teams.map { it.uuid }.any { query.teamUuidsAndNames.map { tUAN -> tUAN.uuid.toString() }.contains(it) }) ||
                        query.visibility == QueryVisibility.PUBLIC ||
                        (query.visibility == QueryVisibility.PRIVATE && query.creatorUsername == username) }.toSet())
    }
}