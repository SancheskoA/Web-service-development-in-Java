package org.lab5.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.coroutines.runBlocking
import org.lab5.CarLib
import java.util.*


class CarServiceClientCLI(
    private val client: HttpClient
) {
    fun run() {
        val scanner = Scanner(System.`in`)

        while (true) {
            println("Выберите действие: a - Получить все машины, c - Добавить машину, r - Получить машину, u - Обновить машину, d - Удалить машину, e - Выйти")
            val choice = scanner.nextLine()

            when (choice) {
                "a" -> {
                    runBlocking {
                        val cars: List<CarLib> = getAllCars()
                        println("All cars")
                        for (car in cars) {
                            println(
                                ("Cars {" + "id=" + car.id
                                        ).toString() + ", make=" + car.make.toString() +
                                        ", model=" + car.model +
                                        ", color=" + car.color +
                                        ", mileage=" + car.mileage +
                                        ", year=" + car.year + '}'
                            )
                        }
                    }
                }
                "c" -> {
                    // Логика добавления машины
                    val car = inputCar()
                    if (car == null) {
                        println("Проверьте корректность введенных данных")
                        continue
                    }
                    runBlocking {
                        createCar(car)
                        println("Машина создана")
                    }
                }
                "r" -> {
                    // Логика получения машины
                    println("Введите ID машины:")
                    val id = scanner.nextLine().toInt()
                    runBlocking {
                        val car = getCar(id)
                        println("Машина с id $id: $car")
                    }
                }
                "u" -> {
                    // Логика обновления машины
                    println("Введите ID машины для обновления:")
                    val id = scanner.nextLine().toInt()

                    val updatedCar = inputCar()
                    if (updatedCar != null) {
                        updatedCar.id = id
                    } else continue;

                    runBlocking {
                        val success = updateCar(id, updatedCar)
                        if (success) println("Машина обновлена успешно") else println("Ошибка при обновлении машины")
                    }
                }
                "d" -> {
                    // Логика удаления машины
                    println("Введите ID машины для удаления:")
                    val id = scanner.nextLine().toInt()
                    runBlocking {
                        val success = deleteCar(id)
                        if (success) println("Машина удалена успешно") else println("Ошибка при удалении машины")
                    }
                }
                "e" -> {
                    println("Выход из программы...")
                    return
                }
                else -> {
                    println("Некорректный ввод. Попробуйте снова.")
                }
            }

        }
    }

    fun inputCar(): CarLib? {
        val scanner = Scanner(System.`in`)

        // Запрашиваем данные для обновления
        println("Введите марку машины:")
        val make = scanner.nextLine()
        println("Введите модель машины:")
        val model = scanner.nextLine()
        println("Введите цвет машины:")
        val color = scanner.nextLine()
        println("Введите пробег машины:")
        val mileageInput = scanner.nextLine()
        val mileage: Int;
        println("Введите год машины:")
        val yearInput = scanner.nextLine()
        val year: Int;

        try {
            year = yearInput?.toInt() ?: throw NumberFormatException("Год не введен")
        } catch (e: NumberFormatException) {
            println("Ошибка: неверный формат года. Пожалуйста, введите число.")
            return null
        }

        try {
            mileage = mileageInput?.toInt() ?: throw NumberFormatException("Пробег не введен")
        } catch (e: NumberFormatException) {
            println("Ошибка: неверный формат пробега. Пожалуйста, введите число.")
            return null
        }

        val car = CarLib(0, make, model, year, color, mileage) // Создаем объект машины
        return car
    }

    suspend fun getAllCars(): List<CarLib> {
        val cars: List<CarLib> = client.get("http://localhost:8080/cars").body()
        return cars
    }

    private suspend fun getCar(id: Int): CarLib {
        val car: CarLib = client.get("http://localhost:8080/cars/$id").body()
        return car
    }

    private suspend fun createCar(car: CarLib): Boolean {
        val response = client.post("http://localhost:8080/cars") {
            contentType(ContentType.Application.Json)
            setBody(car)
        }
        return response.status.value == 200
    }

    private suspend fun updateCar(id: Int, car: CarLib): Boolean {
        val response = client.put("http://localhost:8080/cars/$id") {
            contentType(ContentType.Application.Json)
            setBody(car)
        }
        return response.status.value == 200
    }

    private suspend fun deleteCar(id: Int): Boolean {
        val response = client.delete("http://localhost:8080/cars/$id")
        return response.status.value == 200
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }

            val clientCLI = CarServiceClientCLI(client)
            clientCLI.run()
        }
    }

}

