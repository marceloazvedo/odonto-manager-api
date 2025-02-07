package br.marceloazevedo.odontomanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OdontoManagerApplication

fun main(args: Array<String>) {
    runApplication<OdontoManagerApplication>(*args)
}
