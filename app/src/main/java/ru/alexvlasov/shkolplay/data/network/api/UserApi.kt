package ru.alexvlasov.shkolplay.data.network.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.alexvlasov.shkolplay.data.network.dto.*
import ru.alexvlasov.shkolplay.domain.model.User
import ru.alexvlasov.shkolplay.data.network.ConstantsServer

//Интерфейс для взаимодействия с сервером для пользователя

interface UserApi {
    @GET(ConstantsServer.auth)
    suspend fun me() : User

    @POST("${ConstantsServer.auth}/login")
    suspend fun auth(@Body creds: LoginData) :
            UserTokenData

    @POST("${ConstantsServer.auth}/register")
    suspend fun register(@Body userData: RegisterData) :
            UserTokenData

    @PUT(ConstantsServer.auth)
    suspend fun update(@Body userData: UserData) : User

    @POST("${ConstantsServer.auth}/updatePassword")
    suspend fun updatePassword(@Body userData: UpdatePasswordData)

    @GET("${ConstantsServer.auth}/teachers")
    suspend fun getTeachers() :
            ListResponse<User>
}