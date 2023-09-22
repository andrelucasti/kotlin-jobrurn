package andrelucas.io.business.appointment

interface AppointmentRepository {
    suspend fun save(appointment: Appointment)
}