package core

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.left
import arrow.core.right
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import models.LogEntry

fun interface LogParser {
    operator fun invoke(filePath: String): Either<UnexpectedError, List<LogEntry>>

    companion object {
        fun default() = LogParser { filePath: String ->
            either.eager {
                File(filePath)
                    .readLines()
                    .map { logLine -> logLine.toLogEntry().bind() }
            }
        }
    }
}

private fun String.toLogEntry() = either.eager {
    val csvParts = csvArray().bind()
    LogEntry(csvParts[0], csvParts[1].toLocalDate().bind())
}

private fun String.csvArray() = kotlin.runCatching {
    if (this.indexOf(",") == -1) throw RuntimeException()
    this.split(",").right()
}.getOrElse { UnexpectedError(DATE_FORMAT_ERR_MSG).left() }

private fun String.toLocalDate() = kotlin.runCatching {
    LocalDateTime.parse(this, ISO_OFFSET_DATE_TIME).toLocalDate().right()
}.getOrElse { UnexpectedError(CSV_FORMAT_ERROR_MSG).left() }

private const val CSV_FORMAT_ERROR_MSG =
    "Invalid date format in log entry, required format - 2018-12-09T14:19:00+00:00"
private const val DATE_FORMAT_ERR_MSG =
    "Invalid CSV log format, required format - AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00"