package ru.alexvlasov.shkolplay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.network.AuthInterceptor
import ru.alexvlasov.shkolplay.data.network.api.AchievementApi
import ru.alexvlasov.shkolplay.data.network.api.GroupApi
import ru.alexvlasov.shkolplay.data.network.api.UserApi
import ru.alexvlasov.shkolplay.data.room.dao.UserDao
import ru.alexvlasov.shkolplay.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Реализует объект класса retrofit (описывать взаимодействия в виде интерфейсов)

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MAIN_LINK)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    //Создание классов для работы с сетью на основе интерфейсов, которые мы создали
    // Завод по производству классов для работы с сетью
    @Singleton @Provides
    fun provideUserApi(client: Retrofit): UserApi = client.create(UserApi::class.java)
    @Singleton @Provides
    fun provideAchievementApi(client: Retrofit): AchievementApi = client.create(AchievementApi::class.java)
    @Singleton @Provides
    fun provideGroupApi(client: Retrofit): GroupApi = client.create(GroupApi::class.java)
    // Функция - перехватчик от Retrofit(!)
    @Singleton @Provides
    //Класс для работы с сетью (создание клиента)
    fun okhttpClient(dataStorage: DataStorage, dao: UserDao) : OkHttpClient {
        //Перехватчик запросов на сервак и логгирование
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(dataStorage, dao)) //Добавление токена
            .readTimeout(60, TimeUnit.SECONDS) //Время обработки запроса
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }
}