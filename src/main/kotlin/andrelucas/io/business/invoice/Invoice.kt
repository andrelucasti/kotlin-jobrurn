package andrelucas.io.business.invoice

import andrelucas.io.business.appointment.Appointment
import java.util.*

data class Invoice(val value: Int, val customerId: UUID, val appointmentId: UUID)

fun Appointment.generateSingleInvoice(): Invoice {
    return Invoice(this.value, this.customerId, this.id)
}