package io.andrelucas.com.customer

import java.util.UUID

interface CustomerRepository {
    suspend fun save(customer: Customer)
    suspend fun findById(id: UUID): Customer?
    suspend fun findAll(): List<Customer>
}