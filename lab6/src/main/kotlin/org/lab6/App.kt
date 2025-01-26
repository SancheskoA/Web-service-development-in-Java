package org.lab6

import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.statuspages.*
import java.sql.SQLException


fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            exception<Throwable> { call, e ->
                call.respond(HttpStatusCode.InternalServerError, "Произошла внутренняя ошибка сервера: ${e.message}")
            }
            exception<NumberFormatException> { call, _ ->
                call.respond(HttpStatusCode.BadRequest, "Некорректный формат числа")
            }
            exception<SQLException> { call, e ->
                call.respond(HttpStatusCode.InternalServerError, "Ошибка при запросе в БД: ${e.message}")
            }
        }

        val carDao = CarDAO() // Создание экземпляра DAO

        routing {
            route("/cars") {
                get {
                    call.respond(carDao.listAllCars())
                }
                get("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    id?.let {
                        val car = carDao.getCar(it)
                        if (car != null) call.respond(car) else call.respond(HttpStatusCode.NotFound)
                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                }
                post {
                    val car = call.receive<CarLib>()
                    val created = carDao.insertCar(car)
                    if (created) call.respond(HttpStatusCode.Created) else call.respond(HttpStatusCode.InternalServerError)
                }
                put("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    id?.let {
                        val car = call.receive<CarLib>()
                        val updated = carDao.updateCar(car)
                        if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                }
                delete("{id}") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    id?.let {
                        val deleted = carDao.deleteCar(it)
                        if (deleted) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
                    } ?: call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                }
            }
        }
    }.start(wait = true)
}
