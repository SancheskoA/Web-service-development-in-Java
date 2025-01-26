package org.lab2

import javax.xml.ws.Endpoint

object App {
    @JvmStatic
    fun main(args: Array<String>) {
        val url = "http://localhost:8080/CarService"
        Endpoint.publish(url, CarService())
    }
}
