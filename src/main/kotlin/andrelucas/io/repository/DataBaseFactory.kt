package andrelucas.io.repository

import andrelucas.io.repository.appointment.AppointmentTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseFactory {
    private const val DRIVER = "org.postgresql.Driver"
    private const val URL = "jdbc:postgresql://localhost:5432/remote"
    val datasource: Database = Database.connect(URL, DRIVER, "remote", "remote")

    fun init() {
        // For now ... But, after these tests I'm gonna move this configuration to an application.properties file
        //val database: Database = Database.connect(databaseUrl, driveClassName, "remote", "remote")
        transaction(datasource) {
            SchemaUtils.create(AppointmentTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}