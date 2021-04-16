package ru.lember.el.bot.command.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.lember.el.bot.command.CallbackData
import ru.lember.el.bot.command.CommandInfo
import ru.lember.el.bot.command.ComplexCommandCache
import ru.lember.el.bot.command.ContentWrapper
import ru.lember.el.service.UserInfo
import java.lang.RuntimeException

@Component
class ChooseTimeHandler(
    private val om: ObjectMapper,
    private val cache: ComplexCommandCache
) : CmdHandler {

    override fun cmdInfo(): CommandInfo {
        return CommandInfo.CHOOSE_TIME
    }

    override fun handle(chatId: Long, userInfo: UserInfo, context: String?): SendMessage {

        if (context == null) {
            throw RuntimeException("context can't be null")
        }

        //val callbackData = om.readValue(context, CallbackData::class.java)

        val timeId = context
            //?: throw RuntimeException("timeId can't be null")

        val previousContext: ContentWrapper = cache.get(chatId, cmdInfo().cmdName)

        val fullAppointment: FullAppointment = if (previousContext.exists && previousContext.content != null) {
            om.readValue(
                previousContext.content,
                FullAppointment::class.java
            )
        } else {
            FullAppointment.empty()
        }

        fullAppointment.timeId = timeId

        val json = om.writeValueAsString(fullAppointment)

        cache.save(chatId, cmdInfo().cmdName, json)

        val text = "Вы подтверждаете запись?\n" +
            json

        // todo собирать это все из БД.
        return SendMessage(chatId.toString(), text).apply {
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(InlineKeyboardButton("Да").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "approveAppointment"
                        ))
                    }),
                    listOf(InlineKeyboardButton("Изменить").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "editAppointment"
                        ))
                    })
                )

            }
        }

    }
}