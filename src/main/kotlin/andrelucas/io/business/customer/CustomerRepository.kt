package andrelucas.io.business.customer

import java.util.*

interface CustomerRepository {
    suspend fun save(customer: Customer)
    suspend fun findById(id: UUID): Customer?
    suspend fun findAll(): List<Customer>
}