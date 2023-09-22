package io.andrelucas.com.appointment

import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class Appointment(val id: UUID,
                       val date: LocalDate,
                       val time: LocalTime,
                       val value: Int,
                       val type: AppointmentType,
                       val customerId: UUID) {

    companion object {
        fun single(date: LocalDate, time: LocalTime, value: Int, customerId: UUID): Appointment {
            return Appointment(UUID.randomUUID(), date, time, value, AppointmentType.SINGLE, customerId)
        }
    }
}