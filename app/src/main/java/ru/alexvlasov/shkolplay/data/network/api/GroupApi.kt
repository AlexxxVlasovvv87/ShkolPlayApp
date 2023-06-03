package ru.alexvlasov.shkolplay.data.network.api

import retrofit2.http.*
import ru.alexvlasov.shkolplay.data.network.ConstantsServer
import ru.alexvlasov.shkolplay.data.network.dto.ListResponse
import ru.alexvlasov.shkolplay.domain.model.Achievement
import ru.alexvlasov.shkolplay.domain.model.Group
import java.util.*

//Интерфейс для взаимодействия с сервером для групп

interface GroupApi {

    @GET(ConstantsServer.group)
    suspend fun get() :
            ListResponse<Group>

    @GET("${ConstantsServer.group}/{id}")
    suspend fun get(@Path("id") groupId: UUID) :
            Group

    @POST(ConstantsServer.group)
    suspend fun add(@Query("groupName") groupName: String) :
            Group

    @POST("${ConstantsServer.group}/{id}")
    suspend fun addStudent(@Path("id") id: UUID, @Query("email") email: String)

    @GET("${ConstantsServer.group}/ach/{id}")
    suspend fun achievements(@Path("id") groupId: UUID) :
            ListResponse<Achievement>

    @PUT(ConstantsServer.group)
    suspend fun update(@Body model: Group) :
            Group

    @DELETE("${ConstantsServer.group}/{id}")
    suspend fun delete(@Path("id") id: UUID)

    @DELETE("${ConstantsServer.group}/del/{id}")
    suspend fun deleteFromGroup(@Path("id") id: UUID, @Query("userId") userId: UUID)

}