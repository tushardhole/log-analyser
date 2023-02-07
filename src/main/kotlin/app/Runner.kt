package app

import arrow.core.continuations.either
import arrow.core.left
import core.ActiveLogForTheDay
import core.LogParser
import core.UnexpectedError
import java.time.LocalDate

class Runner {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            either.eager {
                kotlin.runCatching {
                    val runningDirPath =
                        ActiveLogForTheDay::class.java.protectionDomain.codeSource.location.toURI().path
                            .replaceAfterLast("/", "")
                    val fileName = args[0]
                    val requiredDate = LocalDate.parse(args[1])

                    val logEntries = LogParser.default().invoke("${runningDirPath}/${fileName}").bind()
                    val activeCookies = ActiveLogForTheDay.default().invoke(requiredDate, logEntries)

                    activeCookies.forEach {
                        println(it)
                    }
                }.onFailure { UnexpectedError(it.message ?: "UNKNOWN ERROR").left().bind() }
            }.onLeft {
                println("UNKNOWN ERROR - ${it.msg}")
            }
        }
    }
}