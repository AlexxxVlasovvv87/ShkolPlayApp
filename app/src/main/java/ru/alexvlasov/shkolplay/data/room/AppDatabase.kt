package ru.alexvlasov.shkolplay.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.alexvlasov.shkolplay.data.room.dao.UserDao
import ru.alexvlasov.shkolplay.data.room.model.UserEntity

// Держатель базы данных и основной доступ к ним + стандартная реализация
// Интерфейс для взаимодействия с локальной базой данных
// Реализуется в DAOModule

@Database(
    //Как JPA в Spring
    entities = [
        UserEntity::class,
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    //Реализация всех Dao
    abstract fun userDao(): UserDao
    companion object {
        const val DATABASE_NAME = "shkolplay_db"
    }
    suspend fun flushDB() {
        userDao().delete()
    }
}