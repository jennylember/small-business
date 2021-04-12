package ru.lember.el.dao

import ru.lember.el.status.EventStatus
import java.time.Instant

data class EventDo(
    val id: Long,
    val status: EventStatus,
    val orgId: Long,
    val depId: Long,
    val serviceId: Long,
    val masterId: Long,
    val clientId: Long,
    val adminId: Long,
    val start: Instant,
    val finish: Instant,
    val gmtCreate: Instant = Instant.now(),
    val gmtUpdate: Instant = Instant.now()
)