package ru.lember.el.bot.command.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.lember.el.bot.command.CallbackData
import ru.lember.el.bot.command.CommandInfo
import ru.lember.el.bot.command.ComplexCommandCache
import ru.lember.el.service.UserInfo


data class FullAppointment(
    var organizationId: String?,
    var departmentId: String?,
    var masterId: String?,
    var serviceId: String?,
    var dayId: String?,
    var timeId: String?
) {
    companion object {
        fun empty(): FullAppointment {
            return FullAppointment(
                "org1",
                "dep1",
                "master1",
                null,
                null,
                null
            )
        }
    }
}

@Component
class MakeAppointmentHandler(
    private val om: ObjectMapper,
    private val cache: ComplexCommandCache
) : CmdHandler {

    override fun cmdInfo(): CommandInfo {
        return CommandInfo.MAKE_APPOINTMENT
    }

    override fun handle(chatId: Long, userInfo: UserInfo, context: String?): SendMessage {

        cache.save(
            chatId,
            cmdInfo().cmdName,
            om.writeValueAsString(FullAppointment.empty()))

        // todo собирать это все из БД.
        return SendMessage(chatId.toString(), "Выберите услугу").apply {
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(InlineKeyboardButton("Ногти").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "chooseService",
                            context = "serviceId1"
                        ))
                    }),
                    listOf(InlineKeyboardButton("Ресницы").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "chooseService",
                            context = "serviceId2"
                        ))
                    })
                )

            }
        }

    }
}