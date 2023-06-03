package ru.alexvlasov.shkolplay.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.alexvlasov.shkolplay.data.room.model.UserEntity
import java.util.*

// Главный компонент Room. Наследует базу, реализует запросы

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("DELETE FROM users_table WHERE user_id = :id") //Удаление по id
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM users_table LIMIT 1") //asin получение первого пользователя из бд (обновления)
    fun get(): Flow<UserEntity?>

    @Query("SELECT * FROM users_table LIMIT 1") //sin
    fun getUser(): UserEntity?

    @Query("DELETE FROM users_table") //Удаление всего из таблицы
    suspend fun delete()
}
