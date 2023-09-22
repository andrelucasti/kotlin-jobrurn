package andrelucas.io.app

import andrelucas.io.repository.appointment.AppointmentRepositoryImpl
import andrelucas.io.repository.customer.CustomerInMemoryRepositoryImpl
import andrelucas.io.thirdparty.InvoiceIntegratorImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureAppointmentRouting() {
    val appointmentService = AppointmentService(
        customerRepository = CustomerInMemoryRepositoryImpl(),
        appointmentRepository = AppointmentRepositoryImpl(),
        invoiceIntegrator = InvoiceIntegratorImpl()
    )

    routing {
        post("/appointment") {
            appointmentService.createSingle(call.receive()).let {
                call.respondText(contentType = ContentType.Application.Json, text = "Appointment created", status = HttpStatusCode.Created)
            }
        }
    }
}