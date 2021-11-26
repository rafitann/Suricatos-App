package com.app.suricatos.repository

import com.app.suricatos.model.Phone
import com.app.suricatos.model.User
import com.app.suricatos.model.request.RegisterUser
import com.app.suricatos.model.response.PhoneDto
import com.app.suricatos.model.response.UserDto
import com.app.suricatos.model.response.UserResponse
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.extension.toDate

class RegisterRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)
    
    suspend fun register(user: RegisterUser): User {
        return service.register(user).toUser()
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