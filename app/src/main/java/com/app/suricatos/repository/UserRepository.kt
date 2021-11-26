package com.app.suricatos.repository

import com.app.suricatos.model.Phone
import com.app.suricatos.model.User
import com.app.suricatos.model.response.PhoneDto
import com.app.suricatos.model.response.UserResponse
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.extension.toDate

class UserRepository: Repository() {

    val service = retrofit.create(SuricatosService::class.java)

    suspend fun getUser(): User {
        return service.getUser().toUser()
    }

    private fun UserResponse.toUser(): User {
        return User(
            user.id,
            user.name,
            user.birthday.toDate(),
            user.email,
            user.biography,
            user.type,
            phone.toPhone(),
            image,
            user.createdAt.toDate(),
            user.updateAt.toDate()
        )
    }

    private fun PhoneDto.toPhone(): Phone {
        return Phone(
            id,
            ddd,
            number,
            type,
            createdAt.toDate(),
            updateAt.toDate()
        )
    }

}