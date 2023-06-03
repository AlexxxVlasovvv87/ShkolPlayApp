package ru.alexvlasov.shkolplay.presentation.features.student.memory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.room.repositories.AchievementRepository
import ru.alexvlasov.shkolplay.domain.model.Achievement
import ru.alexvlasov.shkolplay.domain.model.CardData
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MemoryGameViewModel @Inject constructor(
    val dataStore: DataStorage,
    val repo: AchievementRepository,
) : ViewModel() {
    var cards by mutableStateOf(listOf<CardData>())
    val openedCards = ArrayList<Int>()
    var idOfFaceUpCard by mutableStateOf(-1)
    var points by mutableStateOf(-1)
    var flips by mutableStateOf(-1)
    var cardsEnabled by mutableStateOf(false)
    var gameFinished by mutableStateOf(false)
    var gameLevel by mutableStateOf(0)
    var achievement: Achievement? = null
    init {
        points = 0
        flips = 0
        launchIO {
            repo.getMe().collectLatest {
                gameLevel = it.memoryLevel
                achievement = it
            }
        }
    }

    fun chooseCard(index: Int) { //Реализация механизма работы карт
        if (!cards[index].isMatched) {
            val matchIndex = idOfFaceUpCard
            if (matchIndex != index)
                flips++
            if (matchIndex != -1 && matchIndex != index) {
                if (cards[matchIndex].emoji == cards[index].emoji) {
                    cards[matchIndex].isMatched = true
                    cards[index].isMatched = true
                    points += 2
                } else {
                    if (openedCards.contains(index)) {
                        points -= 1
                    }
                    if (openedCards.contains(matchIndex)) {
                        points -= 1
                    }
                    openedCards.add(index)
                    openedCards.add(matchIndex)
                }
                cards[index].isFaceUp = true
                idOfFaceUpCard = -1
            } else {
                cards.forEach { it.isFaceUp = false }
                cards[index].isFaceUp = true
                idOfFaceUpCard = index
            }
        }
        points = points
        gameFinished = checkForAllMatchedCards()
        if (gameFinished)
            finishGame()
    }



    fun showCards() = launchIO {
        val difficulty = dataStore.difficulty().first()
        val theme = dataStore.cardTheme().first()
        val showDelay =
        when(difficulty) {
            Difficulty.Easy -> 500L
            Difficulty.Medium -> 350L
            Difficulty.Hard -> 150L
        }
        val createdCards = GameUtils.cards(theme, 8, difficulty)
        cards = arrayListOf() //
        createdCards.forEach {
            cards = cards + it
            delay(150)
        }
        val showIndexes = when(difficulty) { //Список индексов карточек, кот. показывается
            Difficulty.Hard -> cards.indices.shuffled()
            else ->(cards.indices + cards.indices).shuffled()
        }
        showIndexes.forEach {
            cards[it].isFaceUp = true
            idOfFaceUpCard = it
            delay(showDelay)
            cards[it].isFaceUp = false
        }
        idOfFaceUpCard = -1
        cardsEnabled = true
    }

    fun checkForAllMatchedCards() = cards.filter { !it.isMatched }.isEmpty()
    fun finishGame() {
        achievement?.let {
            it.memoryLevel += 1
            it.memoryPoints = maxOf(it.memoryPoints, points)
            launchIO { repo.update(it) }
        }

    }

}