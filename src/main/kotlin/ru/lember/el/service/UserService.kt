package ru.lember.el.service

import org.springframework.stereotype.Service
import ru.lember.el.role.Role


data class UserInfo(
    val id: Long,
    val tgId: Long,
    val exists: Boolean,
    val role: Role
)

interface UserService {

    fun getUserInfo(tgId: Long): UserInfo

}

@Service
class UserServiceImpl: UserService {

    override fun getUserInfo(tgId: Long): UserInfo {

        //todo make implementation
        return UserInfo(tgId, tgId,true, Role.CLIENT)
    }

}

