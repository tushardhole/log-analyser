package models

import java.time.LocalDate

data class LogEntry(
    val cookie: String,
    val date: LocalDate
)