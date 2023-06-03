package ru.alexvlasov.shkolplay.data.network.dto

//Класс данных регистрации

class RegisterData (
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val isTeacher: Boolean
)
