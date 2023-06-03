package ru.alexvlasov.shkolplay.data.network.api

import retrofit2.http.*
import ru.alexvlasov.shkolplay.data.network.ConstantsServer
import ru.alexvlasov.shkolplay.data.network.dto.ListResponse
import ru.alexvlasov.shkolplay.domain.model.Achievement
import java.util.*

// Интерфейс для взаимодействия с сервером
// Retrofit создает объект и отправляет его по пути, указанном в интерфейсе

interface AchievementApi {

    @GET(ConstantsServer.achievement)
    suspend fun get() :
            ListResponse<Achievement>

    @GET("${ConstantsServer.achievement}/me")
    suspend fun getMe() :
            Achievement

    @POST(ConstantsServer.achievement)
    suspend fun add(@Body model: Achievement) :
            Achievement

    @PUT(ConstantsServer.achievement)
    suspend fun update(@Body model: Achievement) :
            Achievement

    @DELETE("${ConstantsServer.achievement}/{id}")
    suspend fun delete(@Path("id") id: UUID)

}