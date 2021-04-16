package ru.lember.el.scheduler.dao

import org.apache.ibatis.annotations.*
import java.time.Instant

@Mapper
interface SchedulerStorage {

    @Insert(INSERT_TASK)
    fun insertTask(
        @Param("name") name: String,
        @Param("job_class_name") jobClassName: String,
        @Param("type") type: SchedulerTaskType,
        @Param("status") status: TaskStatus,
        @Param("time_expression") timeExpression: String,
        @Param("context") context: String,
        @Param("failure_retry_times") failureRetryTimes: String,
        @Param("failure_retry_interval_mills") failureRetryIntervalMills: Long?,
        @Param("now") now: Instant = Instant.now(),
    )

    @Delete(DELETE_TASK)
    fun deleteTask(id: Long): Int

    @Delete(UPDATE_TASK)
    fun updateTask(
        @Param("name") name: String,
        @Param("job_class_name") jobClassName: String,
        @Param("type") type: SchedulerTaskType,
        @Param("status") status: TaskStatus,
        @Param("time_expression") timeExpression: String,
        @Param("context") context: String,
        @Param("failure_retry_times") failureRetryTimes: String,
        @Param("failure_retry_interval_mills") failureRetryIntervalMills: Long?,
        @Param("now") now: Instant = Instant.now(),
    ): Int

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "name", javaType = String::class),
        Arg(column = "job_class_name", javaType = Long::class),
        Arg(column = "type", javaType = SchedulerTaskType::class),
        Arg(column = "status", javaType = TaskStatus::class),
        Arg(column = "time_expression", javaType = String::class),
        Arg(column = "context", javaType = String::class),
        Arg(column = "failure_retry_times", javaType = Int::class),
        Arg(column = "failure_retry_interval_mills", javaType = java.lang.Long::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_TASK)
    fun getTask(id: Long): SchedulerTask?

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "name", javaType = String::class),
        Arg(column = "job_class_name", javaType = Long::class),
        Arg(column = "type", javaType = SchedulerTaskType::class),
        Arg(column = "status", javaType = TaskStatus::class),
        Arg(column = "time_expression", javaType = String::class),
        Arg(column = "context", javaType = String::class),
        Arg(column = "failure_retry_times", javaType = Int::class),
        Arg(column = "failure_retry_interval_mills", javaType = java.lang.Long::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_ALL_TASKS)
    fun getAllTasks(): List<SchedulerTask>

    @Insert(INSERT_TASK_EXECUTION)
    fun insertExecution(
        @Param("task_id") name: Long,
        @Param("execution_start_instant") executionStartInstant: Instant,
        @Param("execution_finish_instant") executionFinishInstant: Instant,
        @Param("status") status: TaskExecutionStatus,
        @Param("details") details: String?,
        @Param("server_ip") serverIp: String?,
        @Param("now") now: Instant = Instant.now(),
    )

    @Delete(DELETE_TASK_EXECUTION)
    fun deleteExecution(id: Long): Int

    @Delete(UPDATE_TASK_EXECUTION_STATUS)
    fun updateExecution(
        @Param("status") status: TaskExecutionStatus,
        @Param("now") now: Instant = Instant.now(),
    ): Int

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "task_id", javaType = Long::class),
        Arg(column = "execution_start_instant", javaType = Instant::class),
        Arg(column = "execution_finish_instant", javaType = Instant::class),
        Arg(column = "status", javaType = TaskExecutionStatus::class),
        Arg(column = "details", javaType = java.lang.String::class),
        Arg(column = "server_ip", javaType = java.lang.String::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_TASK_EXECUTION)
    fun getExecution(id: Long): SchedulerTaskExecution?

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "task_id", javaType = Long::class),
        Arg(column = "execution_start_instant", javaType = Instant::class),
        Arg(column = "execution_finish_instant", javaType = Instant::class),
        Arg(column = "status", javaType = TaskExecutionStatus::class),
        Arg(column = "details", javaType = java.lang.String::class),
        Arg(column = "server_ip", javaType = java.lang.String::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_EXECUTIONS_BY_TASK_ID)
    fun getExecutionsByTaskId(@Param("task_id") name: Long): List<SchedulerTaskExecution>

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "task_id", javaType = Long::class),
        Arg(column = "execution_start_instant", javaType = Instant::class),
        Arg(column = "execution_finish_instant", javaType = Instant::class),
        Arg(column = "status", javaType = TaskExecutionStatus::class),
        Arg(column = "details", javaType = java.lang.String::class),
        Arg(column = "server_ip", javaType = java.lang.String::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_ALL_TASK_EXECUTIONS)
    fun getAllExecutions(): List<SchedulerTaskExecution>

    companion object {

        const val INSERT_TASK = """
            INSERT scheduler_task (
                id, 
                name, 
                job_class_name, 
                type, 
                status, 
                time_expression, 
                context, 
                failure_retry_times, 
                failure_retry_interval_mills, 
                gmt_create, 
                gmt_update
            )
            VALUES (
                nextval('scheduler_task_seq'),
                #{name},
                #{job_class_name},
                #{type},
                #{status},
                #{time_expression},
                #{context},
                #{failure_retry_times},
                #{failure_retry_interval_mills},
                #{now},
                #{now}
            )
        """

        const val UPDATE_TASK = """
            UPDATE scheduler_task
            SET
                name = #{name},
                job_class_name = #{job_class_name},
                type = #{type},
                status = #{status},
                time_expression = #{time_expression},
                context = #{context},
                failure_retry_times = #{failure_retry_times},
                failure_retry_interval_mills = #{failure_retry_interval_mills},
                gmt_update = #{now}
            WHERE id = #{id}
        """

        const val DELETE_TASK = """
            DELETE FROM scheduler_task WHERE id = #{id}
        """

        const val GET_TASK = """
            SELECT
                id, 
                name, 
                job_class_name, 
                type, 
                status, 
                time_expression, 
                context, 
                failure_retry_times, 
                failure_retry_interval_mills, 
                gmt_create, 
                gmt_update
            FROM scheduler_task 
            WHERE id = #{id}
        """

        const val GET_ALL_TASKS = """
            SELECT 
                id, 
                name, 
                job_class_name, 
                type, 
                status, 
                time_expression, 
                context, 
                failure_retry_times, 
                failure_retry_interval_mills, 
                gmt_create, 
                gmt_update
            FROM scheduler_task
        """

        const val GET_TASKS = """
            SELECT 
                id, 
                name, 
                job_class_name, 
                type, 
                status, 
                time_expression, 
                context, 
                failure_retry_times, 
                failure_retry_interval_mills, 
                gmt_create, 
                gmt_update
            FROM scheduler_task
        """

        const val INSERT_TASK_EXECUTION = """
            INSERT scheduler_task_execution (
                id, 
                task_id, 
                execution_start_instant, 
                execution_finish_instant, 
                status, 
                details, 
                server_ip,
                gmt_create, 
                gmt_update
            )
            VALUES (
                nextval('scheduler_task_execution_seq'),
                #{task_id},
                #{execution_start_instant},
                #{execution_finish_instant},
                #{status},
                #{details},
                #{server_ip},
                #{now},
                #{now}
            )
        """

        const val UPDATE_TASK_EXECUTION_STATUS = """
            UPDATE scheduler_task_execution
            SET
                status = #{status},
                gmt_update = #{now}
        """

        const val DELETE_TASK_EXECUTION = """
            DELETE FROM scheduler_task_execution WHERE id = #{id}
        """

        const val GET_TASK_EXECUTION = """
            SELECT
                id, 
                task_id, 
                execution_start_instant, 
                execution_finish_instant, 
                status, 
                details, 
                server_ip,
                gmt_create, 
                gmt_update
            FROM scheduler_task_execution 
            WHERE id = #{id}
        """

        const val GET_EXECUTIONS_BY_TASK_ID = """
            SELECT
                id, 
                task_id, 
                execution_start_instant, 
                execution_finish_instant, 
                status, 
                details, 
                server_ip,
                gmt_create, 
                gmt_update
            FROM scheduler_task_execution 
            WHERE task_id = #{task_id}
        """

        const val GET_ALL_TASK_EXECUTIONS = """
            SELECT 
                id, 
                task_id, 
                execution_start_instant, 
                execution_finish_instant, 
                status, 
                details, 
                server_ip,
                gmt_create, 
                gmt_update
            FROM scheduler_task_execution
        """
    }
}