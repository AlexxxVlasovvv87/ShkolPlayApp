package ru.alexvlasov.shkolplay.domain.model

import java.util.*

//Модель пользователь

class User(
    var uid: UUID? = null,
    var email: String,
    var name: String,
    var surname: String,
    var role: UserRole,
) {

    fun fullName() = "$name $surname"
}