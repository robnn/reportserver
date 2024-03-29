package hu.robnn.reportserver.mapper

import hu.robnn.reportserver.service.queryhelper.NamedParameterStatement
import hu.robnn.auth.service.UserContext
import hu.robnn.reportserver.dao.TeamRepository
import hu.robnn.reportserver.model.dmo.query.*
import hu.robnn.reportserver.model.dto.*
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import kotlin.collections.HashSet

@Component
class QueryMapper(private val teamRepository: TeamRepository) {
    fun mapToQuery(parametrizedQueryRequest: ParametrizedQueryRequest, target: HQuery?, columns: Set<HQueryColumn>) : HQuery {
        var realTarget = target
        if (realTarget == null) {
            realTarget = HQuery()
        }
        realTarget.queryString = parametrizedQueryRequest.queryString
        realTarget.connectionUuid = parametrizedQueryRequest.connectionUuid.toString()
        realTarget.queryName = parametrizedQueryRequest.queryName
        realTarget.queryParameters.clear()
        realTarget.queryParameters.addAll(mapQueryParameters(parametrizedQueryRequest, realTarget).toMutableSet())
        columns.forEach { it.query = realTarget }
        realTarget.queryColumns.clear()
        realTarget.queryColumns.addAll(columns)
        realTarget.creatorUser = UserContext.Companion.currentUser
        realTarget.visibility = parametrizedQueryRequest.visibility
        realTarget.teams.clear()
        realTarget.teams.addAll(parametrizedQueryRequest.teamUuidsAndNames.map { teamRepository.findByUuid(it.name) })
        realTarget.queryCharts.clear()
        realTarget.queryCharts.addAll(mapCharts(parametrizedQueryRequest.charts, realTarget, columns))
        realTarget.queryScheduleData = map(parametrizedQueryRequest.queryScheduleData, realTarget)
        return realTarget
    }

    private fun map(queryScheduleData: QueryScheduleData?, query: HQuery): HQueryScheduleData? {
        if (queryScheduleData == null) {
            return null
        }
        val target = HQueryScheduleData()
        target.date = queryScheduleData.date
        target.day = queryScheduleData.day
        target.query = query
        target.scheduledExecutionType = queryScheduleData.type
        target.timeInDay = queryScheduleData.timeOfDay
        return target
    }

    private fun mapCharts(charts: MutableList<Chart>, query: HQuery, allColumns: Set<HQueryColumn>): MutableSet<HQueryChart> {
        return charts.map { mapChart(it, query, allColumns) }.toMutableSet()
    }

    private fun mapChart(source: Chart, query: HQuery, allColumns: Set<HQueryColumn>): HQueryChart {
        val target = HQueryChart()
        target.chartType = source.chartType
        target.query = query
        target.dataColumn = allColumns.firstOrNull { it.columnName == source.dataColumn }
        target.labelColumn = allColumns.firstOrNull { it.columnName == source.labelColumn }
        query.queryCharts.add(target)
        return target
    }

    private fun mapQueryParameters(parametrizedQueryRequest: ParametrizedQueryRequest, targetQuery: HQuery): Set<HQueryParameter> {
        val parameters = HashSet<HQueryParameter>()
        parametrizedQueryRequest.parameters.forEach {
            val parameter = HQueryParameter()
            parameter.parameterName = it.key
            parameter.parameterValue = it.value.toString()
            parameter.query = targetQuery
            parameters.add(parameter)
        }
        return parameters
    }

    fun mapToRequest(query: HQuery): ParametrizedQueryRequest {
        val target = ParametrizedQueryRequest()
        target.connectionUuid = UUID.fromString(query.connectionUuid)
        target.itemsPerPage = 10
        target.neededPage = 1
        target.queryName = query.queryName
        target.queryString = query.queryString
        target.parameters = mapToRequestParameters(query)
        target.columns = mapToColumns(query.queryColumns)
        target.creatorUsername = query.creatorUser?.username
        target.visibility = query.visibility
        target.teamUuidsAndNames = query.teams.map { TeamUuidAndName(UUID.fromString(it.uuid), it.name) }.toMutableList()
        target.charts = query.queryCharts.map { Chart(it.chartType, it.labelColumn?.columnName, it.dataColumn?.columnName) }.toMutableList()
        target.uuid = query.uuid
        target.executions = query.queryExecutions.map { QueryExecution(uuid = UUID.fromString(it.uuid), executionTime = it.executionTime)}.toMutableList()
        target.queryScheduleData = mapScheduleData(query.queryScheduleData)
        return target
    }

    private fun mapScheduleData(queryScheduleData: HQueryScheduleData?): QueryScheduleData? {
        if (queryScheduleData == null) {
            return null
        }
        val target = QueryScheduleData()
        target.date = queryScheduleData.date
        target.day = queryScheduleData.day
        target.timeOfDay = queryScheduleData.timeInDay
        target.type = queryScheduleData.scheduledExecutionType
        return target
    }

    fun mapToColumns(columns: Set<HQueryColumn>): MutableList<Column> {
        return columns.map { Column(it.columnName, it.columnType) }.toMutableList()
    }

    private fun mapToRequestParameters(query: HQuery): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        query.queryParameters.forEach {
            map[it.parameterName!!] = mapParameterValue(it.parameterValue!!)
        }
        return map
    }

    private fun mapParameterValue(value: String) : Any {
        if (NamedParameterStatement.isParsableToDate(value)) {
            return NamedParameterStatement.parseToDate(value)
        } else {
            try {
                return value.toInt()
            } catch (e: Exception) {
                // not int
            }
            try {
                return value.toLong()
            } catch (e: Exception) {
                // not long
            }
            try {
                return value.toFloat()
            } catch (e: Exception) {
                // not float
            }
            try {
                return value.toDouble()
            } catch (e: Exception) {
                // not double
            }
            //then string
            return value
        }
    }
}