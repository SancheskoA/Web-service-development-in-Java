package org.lab6

import java.sql.*


class CarDAO {
    private var jdbcConnection: Connection? = null

    @Throws(SQLException::class)
    protected fun connect() {
        if (jdbcConnection == null || jdbcConnection!!.isClosed) {
            jdbcConnection = ConnectionUtil.connection;
        }
    }

    @Throws(SQLException::class)
    protected fun disconnect() {
        if (jdbcConnection != null && !jdbcConnection!!.isClosed) {
            jdbcConnection!!.close()
        }
    }

    @Throws(SQLException::class)
    fun insertCar(car: CarLib?): Boolean {
        val sql = "INSERT INTO cars (make, model, color, year, mileage) VALUES (?, ?, ?, ?, ?)"
        connect()

        if (car == null) {
            throw SQLException("car cannot be null")
        }

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, car.make)
        statement.setString(2, car.model)
        statement.setString(3, car.color)
        statement.setInt(4, car.year)
        statement.setInt(5, car.mileage)

        val rowInserted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowInserted
    }

    @Throws(SQLException::class)
    fun listAllCars(): List<CarLib> {
        val listCar: MutableList<CarLib> = ArrayList()

        val sql = "SELECT * FROM cars"

        connect()

        val statement = jdbcConnection!!.createStatement()
        val resultSet = statement.executeQuery(sql)

        while (resultSet.next()) {
            val id = resultSet.getInt("id")
            val make = resultSet.getString("make")
            val model = resultSet.getString("model")
            val color = resultSet.getString("color")
            val year = resultSet.getInt("year")
            val mileage = resultSet.getInt("mileage")

            val car = CarLib(id, make, model, year, color, mileage)
            listCar.add(car)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return listCar
    }

    @Throws(SQLException::class)
    fun getCar(id: Int): CarLib? {
        var car: CarLib? = null
        val sql = "SELECT * FROM cars WHERE id = ?"

        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val make = resultSet.getString("make")
            val model = resultSet.getString("model")
            val color = resultSet.getString("color")
            val year = resultSet.getInt("year")
            val mileage = resultSet.getInt("mileage")

            car = CarLib(id, make, model, year, color, mileage)
        }

        resultSet.close()
        statement.close()

        disconnect()

        return car
    }


    @Throws(SQLException::class)
    fun updateCar(car: CarLib): Boolean {
        val sql = "UPDATE cars SET make = ?, model = ?, color = ?, year = ?, mileage = ? WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setString(1, car.make)
        statement.setString(2, car.model)
        statement.setString(3, car.color)
        statement.setInt(4, car.year)
        statement.setInt(5, car.mileage)
        statement.setInt(6, car.id)

        val rowUpdated = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowUpdated
    }

    @Throws(SQLException::class)
    fun deleteCar(id: Int): Boolean {
        val sql = "DELETE FROM cars WHERE id = ?"
        connect()

        val statement = jdbcConnection!!.prepareStatement(sql)
        statement.setInt(1, id)

        val rowDeleted = statement.executeUpdate() > 0
        statement.close()
        disconnect()
        return rowDeleted
    }
}


