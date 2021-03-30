package ru.lember.el.bot

import org.telegram.telegrambots.meta.api.objects.User

interface BotHandler {

    fun handleUpdate(cmd: String, chatId: Long, messageId: Int, user: User)

}