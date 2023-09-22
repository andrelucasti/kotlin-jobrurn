package io.andrelucas.com.invoice

import io.andrelucas.com.appointment.Appointment
import java.util.UUID

data class Invoice(val value: Int, val customerId: UUID, val appointmentId: UUID)

fun Appointment.generateSingleInvoice(): Invoice {
    return Invoice(this.value, this.customerId, this.id)
}