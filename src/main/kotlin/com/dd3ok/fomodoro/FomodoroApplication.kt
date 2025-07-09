package com.dd3ok.fomodoro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FomodoroApplication

fun main(args: Array<String>) {
    runApplication<FomodoroApplication>(*args)
}
