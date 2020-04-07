package org.uhafactory.kotlinfactory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinFactoryApplication

fun main(args: Array<String>) {
	runApplication<KotlinFactoryApplication>(*args)
}
