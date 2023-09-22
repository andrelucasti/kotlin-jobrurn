package andrelucas.io.repository.appointment

import andrelucas.io.business.appointment.Appointment
import andrelucas.io.business.appointment.AppointmentRepository
import andrelucas.io.repository.DataBaseFactory
import andrelucas.io.repository.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class AppointmentRepositoryImpl: AppointmentRepository {
    override suspend fun save(appointment: Appointment): Unit = dbQuery {
        println("Saving appointment: $appointment")

        transaction(DataBaseFactory.datasource) {
            AppointmentTable.insert { row ->
                row[id] = appointment.id
                row[date] = appointment.date
                row[time] = appointment.time
                row[price] = appointment.value
                row[type] = appointment.type
                row[customerId] = appointment.customerId
                row[createAt] = LocalDateTime.now()
                row[updateAt] = LocalDateTime.now()
            }
        }


    }
}