package io.andrelucas.com.appointment

import io.andrelucas.com.customer.CustomerRepository
import io.andrelucas.com.invoice.InvoiceIntegrator
import io.andrelucas.com.invoice.generateSingleInvoice
import java.lang.RuntimeException


class AppointmentService(
    private val customerRepository: CustomerRepository,
    private val appointmentRepository: AppointmentRepository,
    private val invoiceIntegrator: InvoiceIntegrator
) {

    suspend fun createSingle(appointmentRequest: AppointmentRequest) {
       // customerRepository.findById(appointmentRequest.customerId) ?: throw RuntimeException("Customer ${appointmentRequest.customerId} not found")

        val singleAppointment = Appointment.single(
            appointmentRequest.date,
            appointmentRequest.time,
            appointmentRequest.value,
            appointmentRequest.customerId
        )

        appointmentRepository.save(singleAppointment)
        invoiceIntegrator.send(singleAppointment.generateSingleInvoice())
    }
}