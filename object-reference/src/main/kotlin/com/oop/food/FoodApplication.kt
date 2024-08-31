package com.oop.food

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

// SpringBootApplication Main Class
@SpringBootApplication
class FoodApplication : CommandLineRunner {


    override fun run(vararg args: String?) {
        println("Hello World")
    }

    companion object {
        private val LOG: Logger = getLogger(FoodApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            LOG.info("STARTING THE APPLICATION")
            SpringApplication.run(FoodApplication::class.java, *args)
            LOG.info("APPLICATION FINISHED")
        }
    }
}
