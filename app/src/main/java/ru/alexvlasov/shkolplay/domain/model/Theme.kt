package ru.alexvlasov.shkolplay.domain.model

//Модель тема

enum class Theme(var normalName: String) {
    ANIMALS( "Животные"), CARS("Машины"), FOOD("Еда"), RANDOM("Случайно")
}