package ru.alexvlasov.shkolplay.data.network.dto

//Класс обновления пароля

class UpdatePasswordData(
    val oldPassword: String = "",
    val newPassword: String = ""
)
