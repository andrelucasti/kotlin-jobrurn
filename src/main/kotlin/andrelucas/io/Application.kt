package andrelucas.io

import andrelucas.io.plugins.*
import andrelucas.io.app.configureAppointmentRouting
import andrelucas.io.repository.DataBaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DataBaseFactory.init()
    configureAppointmentRouting()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
