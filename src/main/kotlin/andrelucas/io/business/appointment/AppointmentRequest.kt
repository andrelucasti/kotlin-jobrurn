package andrelucas.io.business.appointment

data class AppointmentRequest(val date: String,
                              val time: String,
                              val value: Int,
                              val customerId: String)