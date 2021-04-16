package ru.lember.el.bot.command.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.lember.el.bot.command.CommandInfo
import ru.lember.el.bot.command.ComplexCommandCache
import ru.lember.el.service.UserInfo


@Component
class ApproveAppointmentHandler(
    private val om: ObjectMapper,
    private val cache: ComplexCommandCache
) : CmdHandler {

    override fun cmdInfo(): CommandInfo {
        return CommandInfo.APPROVE_APPOINTMENT
    }

    override fun handle(chatId: Long, userInfo: UserInfo, context: String?): SendMessage {
        cache.delete(chatId, cmdInfo().cmdName)
        return SendMessage(chatId.toString(), "Вы подтвердили запись!")
    }
}