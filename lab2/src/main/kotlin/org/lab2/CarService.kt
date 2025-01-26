package org.lab2

import com.sun.xml.ws.fault.SOAPFaultBuilder
import java.sql.SQLException
import javax.jws.WebMethod
import javax.jws.WebService


@WebService(serviceName = "CarService")
class CarService {
    var soapFaultBuilder: SOAPFaultBuilder? = null

    @WebMethod(operationName = "getAll")
    open fun getAll(): List<CarLib> {
        val dao = CarDAO()
        return dao.listAllCars()
    }

    @WebMethod(operationName = "createCar")
    open fun createCar(car: CarLib?): Boolean {
        val carDAO = CarDAO()
        try {
            return carDAO.insertCar(car!!)
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    @WebMethod(operationName = "getCar")
    open fun getCar(id: Int): CarLib? {
        val carDAO = CarDAO()
        try {
            return carDAO.getCar(id)
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }
    }

    @WebMethod(operationName = "updateCar")
    open fun updateCar(car: CarLib?): Boolean {
        val carDAO = CarDAO()
        try {
            return carDAO.updateCar(car!!)
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }

    @WebMethod(operationName = "deleteCar")
    open fun deleteCar(id: Int): Boolean {
        val carDAO = CarDAO()
        try {
            return carDAO.deleteCar(id)
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        }
    }
}