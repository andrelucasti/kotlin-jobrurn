package io.andrelucas.com.plugins

import io.ktor.server.application.*
import org.jobrunr.configuration.JobRunr
import org.jobrunr.configuration.JobRunrConfiguration
import org.jobrunr.scheduling.JobScheduler
import org.jobrunr.storage.InMemoryStorageProvider

fun Application.configureJobRunner() {
    jobRunr
}

val jobRunr: JobRunrConfiguration.JobRunrConfigurationResult = JobRunr.configure()
    .useStorageProvider(InMemoryStorageProvider())
    .useBackgroundJobServer()
    .useDashboard()
    .initialize()

val jobScheduler: JobScheduler = jobRunr.jobScheduler