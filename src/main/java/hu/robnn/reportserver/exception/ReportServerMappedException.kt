package hu.robnn.reportserver.exception

import hu.robnn.reportserver.enums.ErrorCauses
import java.lang.RuntimeException

class ReportServerMappedException(val errorCause: ErrorCauses): RuntimeException()