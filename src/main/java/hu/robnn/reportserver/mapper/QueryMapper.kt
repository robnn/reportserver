package hu.robnn.reportserver.mapper

import hu.robnn.reportserver.model.dmo.HQuery
import hu.robnn.reportserver.model.dmo.HQueryParameter
import hu.robnn.reportserver.model.dto.ParametrizedQueryRequest
import hu.robnn.reportserver.service.queryhelper.NamedParameterStatement
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*

@Component
class QueryMapper {
    fun mapToQuery(parametrizedQueryRequest: ParametrizedQueryRequest, target: HQuery?) : HQuery {
        var realTarget = target
        if (realTarget == null) {
            realTarget = HQuery()
        }
        realTarget.queryString = parametrizedQueryRequest.queryString
        realTarget.connectionUuid = parametrizedQueryRequest.connectionUuid.toString()
        realTarget.queryName = parametrizedQueryRequest.queryName
        realTarget.queryParameters = mapQueryParameters(parametrizedQueryRequest, realTarget)
        return realTarget
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
        return target
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