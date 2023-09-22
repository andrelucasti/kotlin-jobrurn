package andrelucas.io.app

import andrelucas.io.business.appointment.Appointment
import andrelucas.io.business.appointment.AppointmentRepository
import andrelucas.io.business.appointment.AppointmentRequest
import andrelucas.io.business.customer.CustomerRepository
import andrelucas.io.business.invoice.InvoiceIntegrator
import andrelucas.io.business.invoice.generateSingleInvoice
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


class AppointmentService(
    private val customerRepository: CustomerRepository,
    private val appointmentRepository: AppointmentRepository,
    private val invoiceIntegrator: InvoiceIntegrator
) {

    suspend fun createSingle(appointmentRequest: AppointmentRequest) {
       // customerRepository.findById(appointmentRequest.customerId) ?: throw RuntimeException("Customer ${appointmentRequest.customerId} not found")

        val singleAppointment = Appointment.single(
            LocalDate.parse(appointmentRequest.date),
            LocalTime.parse(appointmentRequest.time),
            appointmentRequest.value,
            UUID.fromString(appointmentRequest.customerId)
        )

        appointmentRepository.save(singleAppointment)
        invoiceIntegrator.send(singleAppointment.generateSingleInvoice())
    }
}