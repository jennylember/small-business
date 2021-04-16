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
class ChooseServiceHandler(
    private val om: ObjectMapper,
    private val cache: ComplexCommandCache
) : CmdHandler {

    override fun cmdInfo(): CommandInfo {
        return CommandInfo.CHOOSE_SERVICE
    }

    override fun handle(chatId: Long, userInfo: UserInfo, context: String?): SendMessage {

        if (context == null) {
            throw RuntimeException("context can't be null")
        }

        //val callbackData = om.readValue(context, CallbackData::class.java)

        val serviceId = context
           // ?: throw RuntimeException("serviceId can't be null")

        val previousContext: ContentWrapper = cache.get(chatId, cmdInfo().cmdName)

        val fullAppointment: FullAppointment = if (previousContext.exists && previousContext.content != null) {
            om.readValue(
                previousContext.content,
                FullAppointment::class.java
            )
        } else {
            FullAppointment.empty()
        }

        fullAppointment.serviceId = serviceId

        cache.save(chatId, cmdInfo().cmdName, om.writeValueAsString(fullAppointment))

        // todo собирать это все из БД.
        return SendMessage(chatId.toString(), "Выберите день").apply {
            replyMarkup = InlineKeyboardMarkup().apply {
                keyboard = listOf(
                    listOf(InlineKeyboardButton("13/04/2021").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "chooseDay",
                            context = "dayId1"
                        ))
                    }),
                    listOf(InlineKeyboardButton("14/04/2021").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "chooseDay",
                            context = "dayId2"
                        ))
                    }),
                    listOf(InlineKeyboardButton("15/04/2021").apply {
                        this.callbackData = om.writeValueAsString(CallbackData(
                            cmdName = "chooseDay",
                            context = "dayId3"
                        ))
                    })
                )

            }
        }

    }
}