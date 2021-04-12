package ru.lember.el.dao

import org.apache.ibatis.annotations.*

@Mapper
interface ClientStorage {

    @Insert(INSERT_CLIENT)
    fun insert(
        @Param("telegram_id") telegramId: String
    )

    @ConstructorArgs(
        Arg(column = "id", javaType = Long::class),
        Arg(column = "telegram_id", javaType = String::class)
    )
    @Select(GET_CLIENT)
    fun get(id: Long): ClientDo?

    companion object {

        const val INSERT_CLIENT = """
            INSERT INTO client (
                id, telegram_id
            )
            VALUES (
                nextval('client_seq'), 
                #{telegram_id},
            )
        """

        const val GET_CLIENT = """
            SELECT id, telegram_id
            FROM client 
            WHERE id = #{id}
        """
    }
}