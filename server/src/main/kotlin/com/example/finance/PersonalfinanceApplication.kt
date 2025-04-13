package com.example.finance

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.example.finance"])
class PersonalfinanceApplication

fun main(args: Array<String>) {
	runApplication<PersonalfinanceApplication>(*args)
}
