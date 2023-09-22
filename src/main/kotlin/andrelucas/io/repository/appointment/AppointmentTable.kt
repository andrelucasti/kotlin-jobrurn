package andrelucas.io.repository.appointment

import andrelucas.io.business.appointment.AppointmentType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.time

object AppointmentTable: Table(){
    val id = uuid("id")
    val date = date("date")
    val time = time("time")
    val price = integer("price")
    val type = enumerationByName("type", 20, AppointmentType::class)
    val customerId = uuid("customer_id")
    val createAt = datetime("create_at")
    val updateAt = datetime("update_at")

    override val primaryKey = PrimaryKey(id)
}