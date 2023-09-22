package andrelucas.io.thirdparty

import andrelucas.io.business.invoice.Invoice
import andrelucas.io.business.invoice.InvoiceIntegrator

class InvoiceIntegratorImpl: InvoiceIntegrator {
    override suspend fun send(invoice: Invoice) {
        println("Sending invoice $invoice")
    }
}