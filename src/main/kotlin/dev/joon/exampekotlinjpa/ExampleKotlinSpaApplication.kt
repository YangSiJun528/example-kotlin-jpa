package dev.joon.exampekotlinjpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleKotlinSpaApplication

fun main(args: Array<String>) {
    runApplication<ExampleKotlinSpaApplication>(*args)
}
