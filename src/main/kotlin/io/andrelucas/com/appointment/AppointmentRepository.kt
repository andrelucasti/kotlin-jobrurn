package io.andrelucas.com.appointment

interface AppointmentRepository {

    suspend fun save(appointment: Appointment)
}