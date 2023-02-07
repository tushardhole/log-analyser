package core

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import java.time.LocalDate
import models.LogEntry
import org.junit.jupiter.api.Test

internal class LogParserTest {

    @Test
    fun `for empty file should return empty list`() {
        val emptyFilePath = LogParserTest::class.java.getResource("/empty-logs.txt")!!.path

        val logs = LogParser.default().invoke(emptyFilePath)

        logs shouldBeRight emptyList()
    }

    @Test
    fun `should parse from log file in original order`() {
        val testLogFile = LogParserTest::class.java.getResource("/test-logs.txt")!!.path

        val logs = LogParser.default().invoke(testLogFile)

        logs shouldBeRight listOf(
            LogEntry("AtY0laUfhglK3lC7", LocalDate.parse("2018-12-09")),
            LogEntry("SAZuXPGUrfbcn5UA", LocalDate.parse("2018-12-09")),
            LogEntry("5UAVanZf6UtGyKVS", LocalDate.parse("2018-12-09")),
            LogEntry("AtY0laUfhglK3lC7", LocalDate.parse("2018-12-09")),
            LogEntry("SAZuXPGUrfbcn5UA", LocalDate.parse("2018-12-08")),
            LogEntry("4sMM2LxV07bPJzwf", LocalDate.parse("2018-12-08")),
            LogEntry("fbcn5UAVanZf6UtG", LocalDate.parse("2018-12-08")),
            LogEntry("4sMM2LxV07bPJzwf", LocalDate.parse("2018-12-07")),
        )
    }

    @Test
    fun `should throw exceptions if log entry date format is not ISO_OFFSET_DATE_TIME`() {
        val corruptLogFile = LogParserTest::class.java.getResource("/unsupported-date-format-logs.txt")!!.path

        val logs = LogParser.default().invoke(corruptLogFile)

        logs shouldBeLeft UnexpectedError("Invalid date format in log entry, required format - 2018-12-09T14:19:00+00:00")
    }

    @Test
    fun `should throw exceptions if log entry csv format is not correct`() {
        val corruptLogFile = LogParserTest::class.java.getResource("/unsupported-csv-format-logs.txt")!!.path

        val logs = LogParser.default().invoke(corruptLogFile)

        logs shouldBeLeft UnexpectedError("Invalid CSV log format, required format - AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00")
    }
}