package core

import arrow.core.continuations.either
import arrow.core.left
import java.time.LocalDate
import models.LogEntry

fun interface ActiveLogForTheDay {
    operator fun invoke(date: LocalDate, allEntries: List<LogEntry>): List<String>

    companion object {
        fun default() = ActiveLogForTheDay { requiredDate: LocalDate, logEntries: List<LogEntry> ->
            val requiredDateLogs = logEntries
                .filter { it.date == requiredDate }
                .groupBy { it.cookie }

            val activeCookieCount = requiredDateLogs
                .values
                .maxByOrNull { it.size }
                ?.size ?: 0

            requiredDateLogs
                .filter { it.value.size == activeCookieCount }
                .map { it.key }
        }
    }
}