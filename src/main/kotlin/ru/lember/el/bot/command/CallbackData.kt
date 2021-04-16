package ru.lember.el.bot.command

data class CallbackData(
    val cmdName: String,
    val context: String? = null
)