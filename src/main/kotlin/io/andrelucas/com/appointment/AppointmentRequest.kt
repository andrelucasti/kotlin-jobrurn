package io.andrelucas.com.appointment

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class AppointmentRequest(val date: LocalDate, val time: LocalTime, val value: Int, val customerId: UUID)