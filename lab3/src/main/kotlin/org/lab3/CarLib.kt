package org.lab3

class CarLib {
    var id: Int = 0
    var make: String? = null
    var model: String? = null
    var year: Int = 0
    var color: String? = null
    var mileage: Int = 0

    // Конструктор по умолчанию
    constructor()

    // Конструктор с аргументами
    constructor(id: Int, make: String, model: String, year: Int, color: String, mileage: Int) {
        this.id = id
        this.make = make
        this.model = model
        this.year = year
        this.color = color
        this.mileage = mileage
    }

    override fun toString(): String {
        return ("CarService {" + "Id=" + id
                + "make=" + make +
                ", model=" + model +
                ", year=" + year +
                ", color=" + color +
                ", mileage=" + mileage +
                '}')
    }
}
