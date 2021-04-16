package ru.lember.el.scheduler.dao

import java.time.Instant

data class SchedulerTask(
    val id: Long,
    val name: String,
    val jobClassName: String,
    val type: SchedulerTaskType,
    val status: TaskStatus,
    val timeExpression: String,
    val context: String,
    val failureRetryTimes: Int,
    val failureRetryIntervalMills: Long?,
    val gmtCreate: Instant = Instant.now(),
    val gmtUpdate: Instant = Instant.now()
)

enum class SchedulerTaskType {
    RUN_ONCE,
    CRON
}

enum class TaskStatus {
    ENABLED,
    DISABLED,
    FINISHED
}