package andrelucas.io.repository.customer

import andrelucas.io.business.customer.Customer
import andrelucas.io.business.customer.CustomerRepository
import java.util.*

class CustomerInMemoryRepositoryImpl : CustomerRepository {
    override suspend fun save(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: UUID): Customer? {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(): List<Customer> {
        TODO("Not yet implemented")
    }
}