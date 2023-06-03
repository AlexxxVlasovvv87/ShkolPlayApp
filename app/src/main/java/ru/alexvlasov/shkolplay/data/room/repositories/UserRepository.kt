package ru.alexvlasov.shkolplay.data.room.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.network.api.UserApi
import ru.alexvlasov.shkolplay.data.network.dto.LoginData
import ru.alexvlasov.shkolplay.data.network.dto.RegisterData
import ru.alexvlasov.shkolplay.data.network.dto.UpdatePasswordData
import ru.alexvlasov.shkolplay.data.network.dto.UserData
import ru.alexvlasov.shkolplay.data.room.AppDatabase
import ru.alexvlasov.shkolplay.data.room.dao.UserDao
import ru.alexvlasov.shkolplay.data.room.model.UserEntity
import ru.alexvlasov.shkolplay.domain.model.User
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val dao: UserDao,
    private val api: UserApi,
    private val dataStorage: DataStorage,
    private val db: AppDatabase,
) {
    private fun entity(model: User) = UserEntity(model)

    //Получение всех учителей на сервере и в поток асинхронно
    fun getTeachers(): Flow<List<User>> = flow { emit(api.getTeachers().items) }

    //Зарегистрировать пользователя с возвратом токена в кэш
    suspend fun register(
        data: RegisterData,
        onSuccess: () -> Unit = {},
        onError: (message: String) -> Unit = {}
    ) = loadWithIO(
        onNetworkError = onError,
        onSuccess = onSuccess,
    ) {
        val token = api.register(data)
        dataStorage.setAuthToken(token.accessToken)
        fetchMe()
    }

    //Войти, получить токен, токен на локалку, получить пользователя
    suspend fun login(
        data: LoginData,
        onSuccess: suspend () -> Unit = {},
        onError: suspend (message: String) -> Unit = {}
    ) = loadWithIO(
        onNetworkError = onError,
        onSuccess = onSuccess,
    ) {
        val token = api.auth(data)
        dataStorage.setAuthToken(token.accessToken)
        fetchMe()
    }

    //Обновить данные на серваке, пользователь в Entity, сохраняем локально в dao
    suspend fun update(
        data: UserData,
        onSuccess: suspend () -> Unit = {},
        onError: suspend (message: String) -> Unit = {}
    ) = loadWithIO(
        onNetworkError = onError,
        onSuccess = onSuccess,
    ) { dao.insert(entity(api.update(data))) }

    //Обновление пароля
    suspend fun update(
        data: UpdatePasswordData,
        onSuccess: suspend () -> Unit = {},
        onError: suspend (message: String) -> Unit = {}
    ) = loadWithIO(
        onNetworkError = onError,
        onSuccess = onSuccess,
    ) { api.updatePassword(data) }

    //Выход из аккаунта
    suspend fun logout() {
        dao.delete()
        dataStorage.setAuthToken("")
        db.flushDB()
    }

    //Получаем текущего пользователя из локалки
    fun get(): Flow<User?> = dao.get()
        .flowOn(Dispatchers.IO).conflate()
        .map { u -> u?.toModel() }

    //Обращается к бд, получает данные и обновляет данные при получении пользователя
    suspend fun fetchMe() = loadWithIO {
        val user = api.me()
        val localUser = dao.getUser()?.toModel()
        if (user != localUser) {
            dao.delete()
            dao.insert(entity(user))
        }
    }
}