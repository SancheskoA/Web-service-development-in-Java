package org.lab3.client

import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord.NULL
import org.lab3.CarLib
import org.lab3.CarService
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class CarServiceClientCLI(
    private val carService: CarService
) {
    fun run() {
        val scanner = Scanner(System.`in`)
        Runtime.getRuntime().exec("clear")
 
        while (true) {
            println("Выберите действие: c - Добавить машину, r - Получить машину, u - Обновить машину, d - Удалить машину, e - Выйти")
            val choice = scanner.nextLine()

            when (choice) {
                "c" -> {
                    // Логика добавления машины
                    val car = inputCar()
                    val success = carService.createCar(car)
                    if (success) println("Машина добавлена успешно") else println("Ошибка при добавлении машины")
                }
                "r" -> {
                    // Логика получения машины
                    println("Введите ID машины:")
                    val id = scanner.nextLine().toInt()
                    val car = carService.getCar(id)
                    println("Машина: $car")
                }
                "u" -> {
                    // Логика обновления машины
                    println("Введите ID машины для обновления:")
                    val id = scanner.nextLine().toInt()

                    val updatedCar = inputCar()
                    if (updatedCar != null) {
                        updatedCar.id = id
                    };

                    val success = carService.updateCar(updatedCar)
                    if (success) println("Машина обновлена успешно") else println("Ошибка при обновлении машины")
                }
                "d" -> {
                    // Логика удаления машины
                    println("Введите ID машины для удаления:")
                    val id = scanner.nextLine().toInt()
                    val success = carService.deleteCar(id)
                    if (success) println("Машина удалена успешно") else println("Ошибка при удалении машины")
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

    companion object {
        // Методы для взаимодействия с carService (добавление, получение, обновление, удаление машин)
        @JvmStatic
        fun main(args: Array<String>) {
            val carService: CarService = CarService()

            val clientCLI = CarServiceClientCLI(carService)
            clientCLI.run()
        }
    }
}
