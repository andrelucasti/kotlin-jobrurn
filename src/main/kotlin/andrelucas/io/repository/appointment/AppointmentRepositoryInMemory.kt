package andrelucas.io.repository.appointment

import andrelucas.io.business.appointment.Appointment
import andrelucas.io.business.appointment.AppointmentRepository

class AppointmentRepositoryInMemory: AppointmentRepository {
    private val appointments = mutableListOf<Appointment>()

    override suspend fun save(appointment: Appointment) {
        appointments.add(appointment)
    }
}