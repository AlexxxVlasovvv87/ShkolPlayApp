package ru.alexvlasov.shkolplay.domain.model

import java.util.*

//Модель достижения (классы, которые хранят данные)

class Achievement {
    var achId: UUID? = null
    var user: User? = null

    var memoryLevel: Int = 1
    var memoryPoints: Int = 0

    var mathSuccessStreak: Int = 0
    var mathStreak: Int = 0

    fun fullPoint() = (memoryLevel * maxOf(memoryPoints / 4, 1)) +
            mathSuccessStreak * 2 +
            mathStreak
}
