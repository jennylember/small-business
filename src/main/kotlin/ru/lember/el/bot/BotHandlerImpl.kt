package ru.lember.el.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class BotHandlerImpl : BotHandler, TelegramLongPollingBot() {

    override fun handleUpdate(cmd: String, chatId: Long, messageId: Int, user: User) {
        val replyMessage = SendMessage(chatId.toString(), "Привет!")
        tryExecute(replyMessage)
    }

    private fun tryExecute(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {

        }
    }

    override fun onUpdateReceived(update: Update) {

        val message = update.message
        val user = message.from
        val cmd = message.text
        val chatId = message.chatId
        val messageId = message.messageId

        handleUpdate(cmd, chatId, messageId, user)
    }

    override fun getBotUsername(): String {
        return ""
    }

    override fun getBotToken(): String {
        return ""
    }

}
