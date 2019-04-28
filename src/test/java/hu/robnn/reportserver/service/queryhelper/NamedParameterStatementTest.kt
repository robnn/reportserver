package hu.robnn.reportserver.service.queryhelper

import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.mock.ConnectionMock
import org.junit.Test
import kotlin.test.assertEquals

class NamedParameterStatementTest {

    @Test
    fun testParameterExtraction() {
        val connectionMock = ConnectionMock()
        val testQuery = "select * from aaa where id = :firstparam and another = :secondparam and third = :thirdparam"
        val namedParameterStatementTest = NamedParameterStatement(connectionMock, testQuery, mutableMapOf(
                Pair(":firstparam", 1), Pair(":secondparam", 2), Pair(":thirdparam", 3)))
        val extractParams = namedParameterStatementTest.extractParams()
        assertEquals(":firstparam", extractParams[0])
        assertEquals(":secondparam", extractParams[1])
        assertEquals(":thirdparam", extractParams[2])
    }

    @Test(expected = ReportServerMappedException::class)
    fun testNonUniqueParams() {
        val connectionMock = ConnectionMock()
        val testQuery = "select * from aaa where id = :firstparam and another = :firstparam and third = :thirdparam"
        val namedParameterStatementTest = NamedParameterStatement(connectionMock, testQuery, mutableMapOf())
        namedParameterStatementTest.extractParams()
    }
}