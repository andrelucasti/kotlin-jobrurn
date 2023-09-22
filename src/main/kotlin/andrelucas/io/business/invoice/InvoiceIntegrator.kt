package andrelucas.io.business.invoice

interface InvoiceIntegrator {
    suspend fun send(invoice: Invoice)
}