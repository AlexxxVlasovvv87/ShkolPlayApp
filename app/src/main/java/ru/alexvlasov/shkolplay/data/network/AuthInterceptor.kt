package ru.alexvlasov.shkolplay.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.room.dao.UserDao

//Перехватчик всех запросов на сервер, который достаёт токен из DataStorage
//и засоывает в Header для корректных запросов

class AuthInterceptor(val dataStore: DataStorage, val dao: UserDao) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = runBlocking {dataStore.authToken().first() }
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        val response = chain.proceed(requestBuilder.build()) //Получение ответа
        if (response.code == 403) { //Токен не валиден (spring) поток на удаление токена и удаление всех данных
             runBlocking{
                 launch {
                     withContext(Dispatchers.IO){
                         dataStore.deleteToken()
                         dao.delete()
                     }
                 }
             }
        }
        return response
    }
}