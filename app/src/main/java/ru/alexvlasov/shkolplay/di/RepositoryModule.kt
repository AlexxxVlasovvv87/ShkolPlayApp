package ru.alexvlasov.shkolplay.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.datastore.DataStorageImpl
import ru.alexvlasov.shkolplay.data.network.api.*
import ru.alexvlasov.shkolplay.data.room.AppDatabase
import ru.alexvlasov.shkolplay.data.room.dao.*
import ru.alexvlasov.shkolplay.data.room.repositories.*
import javax.inject.Singleton

//Класс для работы с DataStorage (key/value) и Repository (кеш)

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStorage = DataStorageImpl(context)

    //Создание репозиторий
    @Singleton @Provides
    fun provideUserRepository(dao: UserDao, api: UserApi, ds: DataStorage, db: AppDatabase) = UserRepository(dao, api, ds, db)

    @Singleton @Provides
    fun provideGroupRepository(api: GroupApi, achApi: AchievementApi) = GroupRepository(api, achApi)

    @Singleton @Provides
    fun provideAchievementRepository(api: AchievementApi) = AchievementRepository(api)

}