package org.lab1.client

import org.lab1.CarLib
import org.lab1.CarService
import java.net.MalformedURLException
import java.net.URL


object Client {
    @Throws(MalformedURLException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val url = URL("http://localhost:8080/CarService?wsdl")
        val carService: CarService = CarService()

        val cars: List<CarLib> = carService.getAll()
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

        println("Total cars: " + cars.size)

        println("\nCars older than 2020")
        for (car in cars) {
            if (car.year < 2020) {
                System.out.println(
                    ("car {Id=" + car.id).toString() + ", make=" + car.make.toString() + ", model=" +
                            car.model + ", color=" + car.color + ", mileage=" + car.mileage +
                            ", year=" + car.year + "}"
                )
            }
        }

        println("\nWhite cars")
        for (car in cars) {
            if (car.color == "White") {
                System.out.println(
                    ("car {Id=" + car.id + ", make=" + car.make + ", model=" +
                            car.model + ", color=" + car.color + ", mileage=" + car.mileage +
                            ", year=" + car.year + "}"
                ))
            }
        }
    }
}
