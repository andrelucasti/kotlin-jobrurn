package io.andrelucas.com.invoice

interface InvoiceIntegrator {
    suspend fun send(invoice: Invoice)
}