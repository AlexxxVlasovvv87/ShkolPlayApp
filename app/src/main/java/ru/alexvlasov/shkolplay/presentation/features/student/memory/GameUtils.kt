package ru.alexvlasov.shkolplay.presentation.features.student.memory

import ru.alexvlasov.shkolplay.domain.model.CardData
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.domain.model.Theme

object GameUtils {
    private val animals = arrayOf("🐶", "🐱", "🐼", "🦊", "🦁", "🐯", "🐨", "🐮", "🐷", "🐵")
    private val cars = arrayOf("🚔", "🏎", "🚕", "🚚", "🚜", "🚛", "🚑", "🚎", "🚙", "🚒")
    private val food = arrayOf("🍇", "🍌", "🍔", "🎂", "🌽", "🍉", "🍎", "🥕", "🌶", "🍕")
    private val random = arrayOf(
        "🏰", "🐨", "🐝", "🦂", "🦖", "⛄️", "🛸", "💻", "🏁", "💂", "💍", "🐒", "🐊", "🎄", "🏍", "👾",
        "🦁", "🐿", "🔥", "🌘", "🍕", "⚽️", "🥁", "🧀", "🛩", "📸", "🎁", "🍏", "🐩", "🐓", "🍁", "🌈",
        "🦈", "🛏", "📚", "🗿", "🎭", "🍿", "🥥", "🍆", "🦔", "🎮️", "🌶", "🐘", "🚔", "🎡", "🏔", "🚄",
        "🎬", "🐙", "🍄", "🌵", "🐢", "👑", "🧞", "👻", "🕶", "🎓", "🎪", "🐶", "🐲", "🍓", "🏆", "🎰"
    )
    fun cards(theme: Theme, numberOfPairsOfCards: Int, difficulty: Difficulty): List<CardData> {
        val cards = mutableListOf<CardData>()
        val cardEmoji = when(theme) {
            Theme.ANIMALS -> animals
            Theme.CARS -> cars
            Theme.FOOD -> food
            else -> random
        }
        cardEmoji.shuffle()
        for (i in 0 until numberOfPairsOfCards) {
            repeat(2) {
                cards.add(CardData(cardEmoji[i], difficulty.colors.random()))
            }
        }
        cards.shuffle()
        for (i in 0 until cards.size) {
            cards[i].identifier = i
        }
        return cards
    }
}