package ru.alexvlasov.shkolplay.domain.model

//Данные модели настроек

data class CardData(var emoji: String, val color: CardColor) {
    var isFaceUp = false
    var isMatched = false
    var identifier: Int = -1
}

enum class CardColor {
    Neutral, Primary, Secondary, Tertiary;
}

enum class Difficulty(val colors: List<CardColor>, val normalName: String) {
    Easy(listOf(CardColor.Primary), "Легко"),
    Medium(listOf(CardColor.Primary, CardColor.Secondary), "Средне"),
    Hard(listOf(CardColor.Primary, CardColor.Secondary, CardColor.Tertiary), "Сложно")
}