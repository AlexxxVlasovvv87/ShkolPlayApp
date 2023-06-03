package ru.alexvlasov.shkolplay.presentation.features.student.math

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.room.repositories.AchievementRepository
import ru.alexvlasov.shkolplay.domain.model.Achievement
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import java.lang.Integer.max
import javax.inject.Inject

@HiltViewModel
class MathViewModel @Inject constructor(
    val dataStore: DataStorage,
    val repo: AchievementRepository,
) : ViewModel() {
    var answers by mutableStateOf(listOf<Int>())
    var points by mutableStateOf(0)
    var all by mutableStateOf(0)
    var streak by mutableStateOf(0)
    var bestStreak by mutableStateOf(0)
    var result: Int = 0
    var equation: String = ""
    var achievement: Achievement? = null
    init {
        launchIO {
            repo.getMe().distinctUntilChanged().collect {
                achievement = it
                bestStreak = it.mathSuccessStreak
                all = 0
            }
        }
    }

    fun choose(index: Int) {
        if (answers[index] == result) {
            points++
            streak++
        } else {
            points--
            streak = 0
        }

        all++
        save()
        new()
    }

    private fun save() {
        bestStreak = max(bestStreak, streak)
        achievement?.let {
            it.mathStreak = max(all, it.mathStreak)
            it.mathSuccessStreak = max(bestStreak, it.mathSuccessStreak)
            launchIO { repo.update(it) }
        }
    }

    fun new() = launchIO {
        val difficulty = dataStore.difficulty().first()
        val (minLimit, maxLimit) = when (difficulty) {
            Difficulty.Easy -> Pair(1, 20)
            Difficulty.Medium -> Pair(20, 100)
            Difficulty.Hard -> Pair(100, 300)
        }
        val op = Operation.values().random()
        val a = ((-maxLimit..-minLimit) + (minLimit..maxLimit)).random()
        val b = (minLimit..maxLimit).random()
        result = op.equal(a, b)
        equation = "$a ${op.symbol} $b = ?"
        var wrongAnswers = mutableListOf<Int>()
        repeat(5) { wrongAnswers.add(result + ((-10..-1) + (1..10)).random()) }
        Operation.values().filter { it != op }.forEach {
            wrongAnswers.add(it.equal(a, b))
        }
        repeat(2) { wrongAnswers.add(((-maxLimit..-minLimit) + (minLimit..maxLimit)).random()) }
        wrongAnswers = wrongAnswers.filter { it != result }.toSet().shuffled().toMutableList()
        answers = (wrongAnswers.subList(0, 3) + result).shuffled()
    }

}

enum class Operation(val equal: (a: Int, b: Int) -> Int, val symbol: String) {
    Multiply({ a, b -> a * b }, "×"),
    Plus({ a, b -> a + b }, "+"),
    Minus({ a, b -> a - b }, "−"),

}