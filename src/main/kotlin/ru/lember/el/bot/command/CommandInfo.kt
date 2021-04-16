package ru.lember.el.bot.command

import ru.lember.el.role.Role

enum class CommandInfo(val cmdName: String, private val roles: Set<Role>) {
    
    SHOW_APPOINTMENTS("showAppointments", setOf(Role.CLIENT)),
    MAKE_APPOINTMENT("makeAppointment", setOf(Role.CLIENT)),
    APPROVE_APPOINTMENT("approveAppointment", setOf(Role.CLIENT)),
    EDIT_APPOINTMENT("editAppointments", setOf(Role.CLIENT)),
    CANCEL_APPOINTMENT("cancelAppointment", setOf(Role.CLIENT)),
    REVIEW("review", setOf(Role.CLIENT)),
    CHOOSE_SERVICE("chooseService", setOf(Role.CLIENT)),
    CHOOSE_DAY("chooseDay", setOf(Role.CLIENT)),
    CHOOSE_TIME("chooseTime", setOf(Role.CLIENT))
    ;
    
    fun isAllowed(role: Role): Boolean {
        return roles.contains(role)
    }

    companion object {

        fun getAllByRole(role: Role): List<CommandInfo> {
            return values().filter { it.isAllowed(role) }
        }

        fun getByCmd(cmdName: String): CommandInfo? {
            return values().firstOrNull { it.cmdName == cmdName }
        }

    }

}