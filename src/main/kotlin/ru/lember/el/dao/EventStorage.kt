package ru.lember.el.dao

import org.apache.ibatis.annotations.*
import java.time.Instant

@Mapper
interface EventStorage {

    @Insert(INSERT_EVENT)
    fun insert(
        @Param("status") status: String,
        @Param("org_id") orgId: Long,
        @Param("dep_id") depId: Long,
        @Param("service_id") serviceId: Long,
        @Param("master_id") masterId: Long,
        @Param("client_id") clientId: Long,
        @Param("admin_id") adminId: Long,
        @Param("start") start: Instant,
        @Param("finish") finish: Instant,
        @Param("now") gmtCreate: Instant = Instant.now()
    )

    @Delete(DELETE_EVENT)
    fun delete(id: Long): Int

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "status", javaType = String::class),
        Arg(column = "org_id", javaType = Long::class),
        Arg(column = "dep_id", javaType = Long::class),
        Arg(column = "service_id", javaType = Long::class),
        Arg(column = "master_id", javaType = Long::class),
        Arg(column = "client_id", javaType = Long::class),
        Arg(column = "admin_id", javaType = Long::class),
        Arg(column = "start", javaType = Instant::class),
        Arg(column = "finish", javaType = Instant::class),
        Arg(column = "gmt_create", javaType = Instant::class),
        Arg(column = "gmt_update", javaType = Instant::class)
    )
    @Select(GET_EVENT)
    fun get(id: Long): EventDo?

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "status", javaType = String::class),
        Arg(column = "org_id", javaType = Long::class),
        Arg(column = "dep_id", javaType = Long::class),
        Arg(column = "service_id", javaType = Long::class),
        Arg(column = "master_id", javaType = Long::class),
        Arg(column = "client_id", javaType = Long::class),
        Arg(column = "admin_id", javaType = Long::class),
        Arg(column = "start", javaType = Instant::class),
        Arg(column = "finish", javaType = Instant::class)
    )
    @Select(GET_ALL_EVENTS)
    fun getAll(): List<EventDo>

    companion object {

        const val INSERT_EVENT = """
            INSERT INTO event (
                id, status, org_id, dep_id, service_id, master_id, client_id, admin_id, start, finish, gmt_create, gmt_update
            )
            VALUES (
                nextval('event_seq'),
                #{status},
                #{org_id},
                #{dep_id},
                #{service_id},
                #{master_id},
                #{client_id},
                #{admin_id},
                #{start},
                #{finish},
                #{now},
                #{now}
            )
        """

        const val DELETE_EVENT = """
            DELETE FROM event WHERE id = #{id}
        """

        const val GET_EVENT = """
            SELECT id, status, org_id, dep_id, service_id, master_id, client_id, admin_id, start, finish, gmt_create, gmt_update
            FROM event 
            WHERE id = #{id}
        """

        const val GET_ALL_EVENTS = """
            SELECT id, status, org_id, dep_id, service_id, master_id, client_id, admin_id, start, finish, gmt_create, gmt_update
            FROM event
        """
    }
}