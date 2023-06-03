package ru.alexvlasov.shkolplay.domain.model

//Модель данных роли

public enum class UserRole(val fullName: String) {
    Student("Ученик"),
    Teacher("Учитель"),
    Admin("Администратор"),
}
