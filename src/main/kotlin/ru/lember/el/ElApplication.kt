package ru.lember.el

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ElApplication

fun main(args: Array<String>) {
	runApplication<ElApplication>(*args)
}