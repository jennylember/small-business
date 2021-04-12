package ru.lember.el.dao

import org.apache.ibatis.annotations.*

@Mapper
interface AdminStorage {

    @Insert(INSERT_ADMIN)
    fun insert(
        @Param("telegram_id") telegramId: String,
        @Param("org_id") orgId: Long
    )

    @Delete(DELETE_ADMIN)
    fun delete(id: Long): Int

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "telegram_id", javaType = String::class),
        Arg(column = "org_id", javaType = Long::class)
    )
    @Select(GET_ADMIN)
    fun get(id: Long): AdminDo?

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "telegram_id", javaType = String::class),
        Arg(column = "org_id", javaType = Long::class)
    )
    @Select(GET_ALL_ADMINS)
    fun getAll(): List<AdminDo>

    companion object {

        const val INSERT_ADMIN = """
            INSERT INTO admin (
                id, telegram_id, org_id
            )
            VALUES (
                nextval('admin_seq'), 
                #{telegram_id}, 
                #{org_id}
            )
        """

        const val DELETE_ADMIN = """
            DELETE FROM admin WHERE id = #{id}
        """

        const val GET_ADMIN = """
            SELECT id, telegram_id, org_id
            FROM admin 
            WHERE id = #{id}
        """

        const val GET_ALL_ADMINS = """
            SELECT id, telegram_id, org_id
            FROM admin
        """
    }
}