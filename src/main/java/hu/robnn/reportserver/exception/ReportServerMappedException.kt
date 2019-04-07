package hu.robnn.reportserver.exception

import hu.robnn.reportserver.enums.ErrorCause
import java.lang.RuntimeException

class ReportServerMappedException(val errorCauseString: String): RuntimeException() {
    var errorCause: ErrorCause? = null

    constructor(errorCause: ErrorCause) : this(errorCause.name) {
        this.errorCause = errorCause
    }
}