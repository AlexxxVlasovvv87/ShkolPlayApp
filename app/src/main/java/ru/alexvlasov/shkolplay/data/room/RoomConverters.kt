package ru.alexvlasov.shkolplay.data.room

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import ru.alexvlasov.shkolplay.domain.model.UserRole

//Конвертер для преобразования объекта роли в строку и наоборот

object RoomConverters {
    private val gson = GsonBuilder().setLenient().create()

    @TypeConverter
    fun stringToRole(name: String): UserRole = UserRole.valueOf(name)
    @TypeConverter
    fun roleToString(role: UserRole): String = role.name
}