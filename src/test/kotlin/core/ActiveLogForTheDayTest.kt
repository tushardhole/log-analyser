package core

import io.kotest.matchers.shouldBe
import java.time.LocalDate
import models.LogEntry
import org.junit.jupiter.api.Test

internal class ActiveLogForTheDayTest {

    @Test
    fun `should return empty for empty logs`() {
        val logEntries = emptyList<LogEntry>()

        val activeCookies = ActiveLogForTheDay.default().invoke(LocalDate.now(), logEntries)

        activeCookies shouldBe emptyList()
    }

    @Test
    fun `should most active log of the day for log entries of only one day`() {
        val logEntries = listOf<LogEntry>(
            LogEntry("test", LocalDate.parse("2018-12-09")),
            LogEntry("test", LocalDate.parse("2018-12-09")),
            LogEntry("test2", LocalDate.parse("2018-12-09")),
        )

        val activeCookies = ActiveLogForTheDay.default().invoke(LocalDate.parse("2018-12-09"), logEntries)

        activeCookies shouldBe listOf("test")
    }

    @Test
    fun `should most active log of the day for log entries of multiple days`() {
        val logEntries = listOf<LogEntry>(
            LogEntry("test", LocalDate.parse("2018-12-11")),
            LogEntry("test", LocalDate.parse("2018-12-09")),
            LogEntry("test", LocalDate.parse("2018-12-09")),
            LogEntry("xyz", LocalDate.parse("2018-12-10")),
            LogEntry("xyz", LocalDate.parse("2018-12-11")),
            LogEntry("xyz", LocalDate.parse("2018-12-11")),
        )

        val activeCookies = ActiveLogForTheDay.default().invoke(LocalDate.parse("2018-12-11"), logEntries)

        activeCookies shouldBe listOf("xyz")
    }

    @Test
    fun `should multiple most active log of the day`() {
        val logEntries = listOf<LogEntry>(
            LogEntry("test", LocalDate.parse("2018-12-11")),
            LogEntry("test", LocalDate.parse("2018-12-11")),
            LogEntry("temp", LocalDate.parse("2018-12-11")),
            LogEntry("test2", LocalDate.parse("2018-12-11")),
            LogEntry("test2", LocalDate.parse("2018-12-11"))
        )

        val activeCookies = ActiveLogForTheDay.default().invoke(LocalDate.parse("2018-12-11"), logEntries)

        activeCookies shouldBe listOf("test", "test2")
    }

    @Test
    fun `should return empty for no active logs for given day are present`() {
        val logEntries = listOf<LogEntry>(
            LogEntry("test", LocalDate.parse("2018-12-09"))
        )

        val activeCookies = ActiveLogForTheDay.default().invoke(LocalDate.parse("2017-12-09"), logEntries)

        activeCookies shouldBe emptyList()
    }
}