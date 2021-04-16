package ru.lember.el.scheduler.dao

import java.time.Instant

data class SchedulerTaskExecution(
    val id: Long,
    val taskId: Long,
    val executionStartInstant: Instant,
    val executionFinishInstant: Instant,
    val status: TaskExecutionStatus,
    val details: String?,
    val serverIp: String?,
    val gmtCreate: Instant = Instant.now(),
    val gmtUpdate: Instant = Instant.now()
)

enum class TaskExecutionStatus {
    SUCCESS,
    CANCELLED,
    ERROR
}