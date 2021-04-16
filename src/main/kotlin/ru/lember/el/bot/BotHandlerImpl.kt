package ru.lember.el.bot

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.lember.el.bot.command.CallbackData
import ru.lember.el.bot.command.CommandInfo
import ru.lember.el.bot.command.handlers.CmdHandler
import ru.lember.el.service.UserService
import javax.ws.rs.client.Entity.json


@Service
class BotHandlerImpl(
    val objectMapper: ObjectMapper,
    private val userService: UserService,
    cmdHandlersList: List<CmdHandler>
) : BotHandler, TelegramLongPollingBot() {

    private val cmdHandlers: Map<CommandInfo, CmdHandler>
        = cmdHandlersList.associateBy { it.cmdInfo() }

    override fun handleUpdate(cmd: String, chatId: Long, messageId: Int, user: User) {
        val replyMessage = SendMessage(chatId.toString(), "Что вы хотите сделать?")

        val replyMarkup = InlineKeyboardMarkup().apply {
            keyboard = listOf(
                listOf(InlineKeyboardButton("Просмотреть записи").apply {
                    this.callbackData = objectMapper.writeValueAsString(CallbackData(
                        cmdName = "showAppointments"
                    ))
                }),
                listOf(InlineKeyboardButton("Записаться к мастеру").apply {
                    this.callbackData = objectMapper.writeValueAsString(CallbackData(
                        cmdName = "makeAppointment"
                    ))
                }),
                listOf(InlineKeyboardButton("Изменить запись").apply {
                    this.callbackData = objectMapper.writeValueAsString(CallbackData(
                        cmdName = "editAppointments"
                    ))
                }),
                listOf(InlineKeyboardButton("Отменить запись").apply {
                    this.callbackData = objectMapper.writeValueAsString(CallbackData(
                        cmdName = "cancelAppointment"
                    ))
                }),
                listOf(InlineKeyboardButton("Оставить отзыв").apply {
                    this.callbackData = objectMapper.writeValueAsString(CallbackData(
                        cmdName = "review"
                    ))
                })
            )

        }


//        val replyMarkup = ReplyKeyboardMarkup()
//        replyMarkup.resizeKeyboard = false
//        replyMarkup.selective = false
//        replyMarkup.oneTimeKeyboard = false
//        replyMarkup.keyboard = listOf(
//            KeyboardRow().apply {
//                this.add("3")
//            },
//            KeyboardRow().apply {
//                this.add("5")
//            },
//            KeyboardRow().apply {
//                this.add("4")
//            },
//        )
        replyMessage.replyMarkup = replyMarkup
        //val replyMessage = SendMessage(chatId.toString(), "Привет!")
        tryExecute(replyMessage)
    }

    private fun tryExecute(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {

        }
    }

    override fun onUpdateReceived(update: Update) {

        val user = getUser(update)

        val info = userService.getUserInfo(user.id)

        if (update.hasCallbackQuery()) {

            val callbackData: CallbackData = objectMapper.readValue(update.callbackQuery.data, CallbackData::class.java);
            val cmdName = callbackData.cmdName
            val cmdInfo = CommandInfo.getByCmd(cmdName)
            if (cmdInfo != null && cmdInfo.isAllowed(info.role)) {
                cmdHandlers[cmdInfo]?.handle(getMessage(update).chatId, info, callbackData.context)?.let { tryExecute(it) }
            }

        } else {

            val message = update.message

            val cmd = message.text
            val chatId = message.chatId
            val messageId = message.messageId

            handleUpdate(cmd, chatId, messageId, user)
        }
    }

    private fun getUser(update: Update): User {

        if (update.hasMessage()) {
            return update.message.from
        } else if (update.hasCallbackQuery()) {
            return update.callbackQuery.from
        }

        throw RuntimeException("update $update is broken")
    }

    private fun getMessage(update: Update): Message {

        if (update.hasMessage()) {
            return update.message
        } else if (update.hasCallbackQuery()) {
            return update.callbackQuery.message
        }

        throw RuntimeException("update $update is broken")
    }

    override fun getBotUsername(): String {
        return ""
    }

    override fun getBotToken(): String {
        return ""
    }

}
