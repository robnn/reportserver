package hu.robnn.reportserver.service.queryhelper

import hu.robnn.reportserver.enums.QueryErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.util.*

/**
 * Query class for named parameters, like :id
 * Injection safe because uses PreparedStatement
 * Only works for names containing lower, upper case letters and digits
 */
class NamedParameterStatement(private val connection: Connection,
                              private val query: String,
                              private val parameters: Map<String, Any>) {

    companion object {
        fun extractParams(query: String): List<String> {
            val regex = ":[a-zA-Z0-9]+".toRegex()
            val matchesIndex = regex.findAll(query)
            val paramsList = matchesIndex.map { query.substring(it.range) }.toList()
            val distinctParams = paramsList.distinct()
            if (distinctParams.size != paramsList.size) {
                throw ReportServerMappedException(QueryErrorCause.NON_UNIQUE_PARAMETER_NAMES)
            }
            return paramsList
        }
    }

    init {
        validateParameterMatchInQuery()
    }

    private fun validateParameterMatchInQuery() {
        val paramsInQuery = extractParams()
        val paramsSupplied = parameters.keys
        paramsInQuery.forEach {
            if (!paramsSupplied.contains(it)) {
                throw ReportServerMappedException(QueryErrorCause.PARAMS_IN_QUERY_NOT_PROVIDED)
            }
        }
        paramsSupplied.forEach {
            if (!paramsInQuery.contains(it)) {
                throw ReportServerMappedException(QueryErrorCause.NOT_NEEDED_PARAMS_PROVIDED)
            }
        }
    }

    @Throws(Exception::class)
    fun executeQuery(): ResultSet {
        val paramNamesInOrder = extractParams()
        var queryWithQuestionMarks = query
        paramNamesInOrder.forEach { queryWithQuestionMarks = queryWithQuestionMarks.replace(it, "?") }

        val prepareStatement = connection.prepareStatement(queryWithQuestionMarks)
        setParamsForStatement(prepareStatement, parameters, paramNamesInOrder)

        try {
            return prepareStatement.executeQuery()
        } catch (e: java.lang.Exception) {
            throw ReportServerMappedException(e.message!!)
        }
    }

    private fun setParamsForStatement(prepareStatement: PreparedStatement, parameters: Map<String, Any>, paramNamesInOrder: List<String>) {
        var i = 1
        paramNamesInOrder.forEach { paramName ->
            val param = parameters[paramName]
            when (param) {
                is Date -> prepareStatement.setTimestamp(i++, Timestamp(param.time))
                is Int -> prepareStatement.setInt(i++,  param)
                is Long -> prepareStatement.setLong(i++, param)
                is Double -> prepareStatement.setDouble(i++, param)
                is Float -> prepareStatement.setFloat(i++, param)
                else -> prepareStatement.setString(i++, param as String)
            }
        }
    }

    fun extractParams(): List<String> = Companion.extractParams(query)
}