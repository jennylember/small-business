package ru.lember.el.bot.command.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.lember.el.bot.command.CommandInfo
import ru.lember.el.service.UserInfo

interface CmdHandler {

    fun cmdInfo(): CommandInfo
    fun handle(chatId: Long, userInfo: UserInfo, context: String?): SendMessage

}