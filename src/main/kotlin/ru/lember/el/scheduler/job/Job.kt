package ru.lember.el.scheduler.job

import ru.lember.el.scheduler.dao.TaskExecutionStatus

data class JobExecutionResult(
    val resultType: TaskExecutionStatus,
    val details: String?
)

interface Job {
    fun process(context: String): JobExecutionResult
}