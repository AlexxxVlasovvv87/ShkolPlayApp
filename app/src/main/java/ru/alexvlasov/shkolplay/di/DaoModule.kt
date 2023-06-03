package ru.alexvlasov.shkolplay.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexvlasov.shkolplay.data.room.AppDatabase
import javax.inject.Singleton

//DAO - интерфейс для взаимодействия с локальной базой данных (как repository в Spring)
// Модуль Hilt для appdb
// Используется компонент синглтон (отвечает за предоставление глобальных звисимостей)
// Живёт столько, сколько живёт приложение
@InstallIn(SingletonComponent::class)
@Module
object DaoModule {

    // Аннотация Singleton говорит о создании всего лишь одного объекта без дублирования и дальнейшей работы с ним
    // Аннотация Provides говорит о том, что у нас нет прямого доступа к классу,
    // либо процесс создания объекта достаточно сложный
    @Singleton @Provides
    fun provideAppDB(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

    @Singleton @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

}