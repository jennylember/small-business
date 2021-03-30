package ru.lember.el.bot

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.function.Consumer
import javax.annotation.PostConstruct


class BotStarterImpl(private var bots: List<LongPollingBot>) :
        BotStarter, ApplicationContextAware {

    @PostConstruct
    private fun postConstruct() {

    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {

        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

        bots.forEach(Consumer<LongPollingBot> { bot: LongPollingBot? ->
            try {
                telegramBotsApi.registerBot(bot)
            } catch (e: TelegramApiRequestException) {
                //log.error("Start running bot error: ", e)
                throw RuntimeException(e)
            }
        })
    }
}