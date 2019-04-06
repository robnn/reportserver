package hu.robnn.reportserver.exception

import hu.robnn.reportserver.enums.ErrorCause
import java.lang.RuntimeException

class ReportServerMappedException(val errorCause: ErrorCause): RuntimeException()