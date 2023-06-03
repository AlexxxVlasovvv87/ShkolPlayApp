package ru.alexvlasov.shkolplay.data.room.repositories

import kotlinx.coroutines.flow.*
import ru.alexvlasov.shkolplay.data.network.api.AchievementApi
import ru.alexvlasov.shkolplay.data.network.api.GroupApi
import ru.alexvlasov.shkolplay.domain.model.Achievement
import ru.alexvlasov.shkolplay.domain.model.Group
import java.util.*
import javax.inject.Inject


class GroupRepository @Inject constructor(
    private val api: GroupApi,
    private val achApi: AchievementApi
) {
    fun get(): Flow<List<Group>> = flow { emit(api.get().items) } //получение списка всех групп
    fun getAchievements(id: UUID): Flow<List<Achievement>> = flow { emit(api.achievements(id).items) } //достижение по опред группе
    fun getAchievements(): Flow<List<Achievement>> = flow { emit(achApi.get().items) } //Общие достижения всех пользователей

    //создаёт группу по названию
    suspend fun add(
        groupName: String,
        onError: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    ) = loadWithIO(onError = onError, onSuccess = onSuccess) {
        api.add(groupName)
    }

    suspend fun addStudent(
        id: UUID,
        email: String,
        onError: suspend () -> Unit = {},
        onSuccess: suspend () -> Unit = {}
    ) = loadWithIO(onError = onError, onSuccess = onSuccess) {
        api.addStudent(id, email)
    }

    suspend fun update(modelToUpdate: Group) = loadWithIO() {
        api.update(modelToUpdate)
    }

    suspend fun delete(id: UUID) = loadWithIO {
        api.delete(id)
    }

    suspend fun deleteFromGroup(id: UUID, userId: UUID) = loadWithIO {
        api.deleteFromGroup(id, userId)
    }
}