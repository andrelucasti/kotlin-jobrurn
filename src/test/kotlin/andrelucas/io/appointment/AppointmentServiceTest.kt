package andrelucas.io.appointment

import andrelucas.io.app.AppointmentService
import andrelucas.io.business.appointment.Appointment
import andrelucas.io.business.appointment.AppointmentRepository
import andrelucas.io.business.appointment.AppointmentRequest
import andrelucas.io.business.appointment.AppointmentType
import andrelucas.io.business.customer.Customer
import andrelucas.io.business.customer.CustomerRepository
import andrelucas.io.business.invoice.Invoice
import andrelucas.io.business.invoice.InvoiceIntegrator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.test.*

class AppointmentServiceTest {

    private lateinit var customerRepository: CustomerRepository
    private lateinit var appointmentRepository: AppointmentRepository
    private lateinit var invoiceIntegrator: InvoiceIntegrator

    private lateinit var appointmentService: AppointmentService

    private val mainTestThread = newSingleThreadContext("main test thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainTestThread)

        customerRepository = mockk<CustomerRepository>()
        appointmentRepository = mockk<AppointmentRepository>()
        invoiceIntegrator = mockk<InvoiceIntegrator>()
        appointmentService = AppointmentService(customerRepository, appointmentRepository, invoiceIntegrator)

        invoiceIntegrator = mockk<InvoiceIntegrator> {
            coEvery { send(any()) }.answers { nothing }
        }
    }

    @Test
    @Ignore
    fun shouldNotCreateSingleAppointmentWhenCustomerIsNotRegistered() = runTest {
        customerRepository = mockk<CustomerRepository> {
            coEvery { findById(any()) } answers { null }
        }

        val appointmentService = AppointmentService(customerRepository, appointmentRepository, invoiceIntegrator)

        assertFailsWith(RuntimeException::class, "Customer ${UUID.randomUUID()} not found") {
            appointmentService.createSingle(AppointmentRequest("2023-04-17", "17:00:00", 10000, "4b1a1ba4-b473-41d8-9206-0929946be419"))
        }
    }

    @Test
    fun shouldCreateSingleAppointmentWhenCustomerIsRegistered() = runTest {
        val appointmentSlot = slot<Appointment>()
        val customerId = UUID.randomUUID()
        customerRepository = mockk<CustomerRepository> {
            coEvery { findById(customerId) } answers { Customer(customerId) }
        }

        appointmentRepository = mockk<AppointmentRepository>{
            coEvery { save(any()) }.answers { nothing }
        }

        val appointmentService = AppointmentService(customerRepository, appointmentRepository, invoiceIntegrator)
        val appointmentRequest = AppointmentRequest("2023-04-17", "17:00:00", 10000, customerId.toString())

        appointmentService.createSingle(appointmentRequest)

        coVerify { appointmentRepository.save(appointment = capture(appointmentSlot)) }

        assertNotNull(appointmentSlot.captured.id)
        assertEquals(expected = LocalDate.parse(appointmentRequest.date), actual = appointmentSlot.captured.date)
        assertEquals(expected = LocalTime.parse(appointmentRequest.time), actual = appointmentSlot.captured.time)
        assertEquals(expected = appointmentRequest.value, actual = appointmentSlot.captured.value)
        assertEquals(expected = AppointmentType.SINGLE, actual = appointmentSlot.captured.type)
        assertEquals(expected = customerId, actual = appointmentSlot.captured.customerId)
    }

    @Test
    fun shouldCreateInvoiceWhenAppointmentIsCreated() = runTest {
        val invoiceSlot = slot<Invoice>()
        val appointmentSlot = slot<Appointment>()

        val customerId = UUID.randomUUID()
        customerRepository = mockk<CustomerRepository> {
            coEvery { findById(customerId) } answers { Customer(customerId) }
        }

        appointmentRepository = mockk<AppointmentRepository>{
            coEvery { save(any()) }.answers { nothing }
        }

        invoiceIntegrator = mockk<InvoiceIntegrator> {
            coEvery { send(any()) }.answers { nothing }
        }

        val appointmentService = AppointmentService(customerRepository, appointmentRepository, invoiceIntegrator)
        val appointmentRequest = AppointmentRequest("2023-04-17", "17:00:00", 10000, customerId.toString())

        appointmentService.createSingle(appointmentRequest)

        coVerify { appointmentRepository.save(appointment = capture(appointmentSlot)) }
        coVerify { invoiceIntegrator.send(invoice = capture(invoiceSlot)) }

        assertEquals(expected = appointmentRequest.value, actual = invoiceSlot.captured.value)
        assertEquals(expected = customerId, actual = invoiceSlot.captured.customerId)
        assertEquals(expected = appointmentSlot.captured.id, actual = invoiceSlot.captured.appointmentId)
    }
}
