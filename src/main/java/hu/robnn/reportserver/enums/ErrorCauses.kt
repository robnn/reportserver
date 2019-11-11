package hu.robnn.reportserver.enums

interface ErrorCause {
    val name: String
    fun getCause(): String {
        return this.name
    }
}

enum class DriverErrorCause: ErrorCause {
    NOT_VALID_JDBC_DRIVER,
    NOT_A_JAR_FILE
}

enum class QueryErrorCause: ErrorCause {
    QUERY_AND_UUID_MUST_NOT_BE_NULL,
    NON_UNIQUE_PARAMETER_NAMES,
    PARAMS_IN_QUERY_NOT_PROVIDED,
    NOT_NEEDED_PARAMS_PROVIDED,
    NO_QUERY_NAME_SUPPLIED,
}

enum class TeamErrorCause: ErrorCause {
    TEAM_NAME_MUST_BE_SUPPLIED,
    TEAM_MUST_HAVE_USERS_IN_IT,
}

enum class DashboardErrorCause: ErrorCause {
    USER_NOT_FOUND,
    DASHBOARD_FOR_USER_NOT_FOUND,
}

enum class SchedulingErrorCause: ErrorCause {
    ONE_TIME_DATE_MUST_SUPPLIED,
    WEEKLY_DAY_MUST_SUPPLIED,
    WEEKLY_TIME_OF_DAY_MUST_SUPPLIED,
    DAILY_TIME_OF_DAY_MUST_SUPPLIED,
    INVALID_TIME_OF_DAY,
}