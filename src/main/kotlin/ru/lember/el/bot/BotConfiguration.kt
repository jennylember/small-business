package ru.lember.el.bot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.generics.LongPollingBot

@Configuration
class BotConfiguration {

    @Bean
    fun botStarter(botHandlers: List<LongPollingBot>): BotStarter {
        return BotStarterImpl(botHandlers)
    }

    @Bean
    fun botHandler(): LongPollingBot {
        return BotHandlerImpl()
    }

}

