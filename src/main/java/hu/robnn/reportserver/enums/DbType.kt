package hu.robnn.reportserver.enums

/**
 * The base setup of jdbc string will look like:
 * "jdbc:${baseString}{$separator}${host}:${port}/${dbName}"
 * where baseString and separator comes from the DbType enumeration
 */

enum class DbType(val baseString: String, val separator: String) {
    ORACLE_THIN("oracle:thin", ":@//"),
    MY_SQL("mysql", "://"),
    MICROSOFT_SQL_SERVER("sqlserver", "://"),
    POSTGRESQL("postgresql","://"),
    DB2("db2","://"),
    SQLITE("sqlite", ":")

    ;

    fun buildJdbcString(hostname: String, port: String, dbName: String): String =
            "jdbc:$baseString$separator$hostname:$port/$dbName"

}