package ru.lember.el.bot.command

import org.springframework.stereotype.Service

interface ComplexCommandCache {

    fun save(chatId: Long, cmdName: String, context: String?)
    fun get(chatId: Long, cmdName: String): ContentWrapper
    fun delete(chatId: Long, cmdName: String)

}


data class ContentWrapper(
    val exists: Boolean,
    val content: String?
)

@Service
class ComplexCommandInMemCache: ComplexCommandCache {

    private val map: MutableMap<String, String?> = mutableMapOf()

    override fun save(chatId: Long, cmdName: String, context: String?) {
        map[composeKey(chatId, cmdName)] = context
    }

    override fun get(chatId: Long, cmdName: String): ContentWrapper {
        val content = map[composeKey(chatId, cmdName)]
        return if (content == null) {
            ContentWrapper(false, null)
        } else {
            ContentWrapper(true, content)
        }
    }

    override fun delete(chatId: Long, cmdName: String) {
        map.remove(composeKey(chatId, cmdName))
    }

    private fun composeKey(chatId: Long, cmdName: String): String {
        return "" + chatId + cmdName
    }

}