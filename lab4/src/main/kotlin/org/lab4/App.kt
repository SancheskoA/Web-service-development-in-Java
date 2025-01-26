package org.lab4

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }

        val carDao = CarDAO() // Создание экземпляра DAO

        routing {
            route("/cars") {
                get {
                    call.respond(carDao.listAllCars())
                }
            }
        }
    }.start(wait = true)
}
