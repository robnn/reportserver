package hu.robnn.reportserver.enums

interface ErrorCauses {
    fun getCause(): String
}

enum class DriverErrorCause: ErrorCauses {
    NOT_VALID_JDBC_DRIVER,
    NOT_A_JAR_FILE
    ;

    override fun getCause(): String = this.name
}