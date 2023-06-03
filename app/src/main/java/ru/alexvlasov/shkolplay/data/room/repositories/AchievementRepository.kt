package ru.alexvlasov.shkolplay.data.room.repositories

import kotlinx.coroutines.flow.*
import ru.alexvlasov.shkolplay.data.network.api.AchievementApi
import ru.alexvlasov.shkolplay.domain.model.Achievement
import javax.inject.Inject

//Работа с достижениями

class AchievementRepository @Inject constructor(
    //получает api
    private val api: AchievementApi
) {
    fun get(): Flow<List<Achievement>> = flow { emit(api.get().items) } //запрашиваем достижения всех пользователей
    fun getMe(): Flow<Achievement> = flow { emit(api.getMe()) } //достижения текущего пользователя

    //методы добавления (не исп), обновления, удаления(не исп)
    suspend fun add(
        achievement: Achievement,
        onError: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    ) = loadWithIO(onError = onError, onSuccess = onSuccess) {
        api.add(achievement)
    }

    suspend fun update(modelToUpdate: Achievement) = loadWithIO() {
        api.update(modelToUpdate)
    }

    suspend fun delete(model: Achievement) = loadWithIO {
        api.delete(model.achId!!)
    }
}