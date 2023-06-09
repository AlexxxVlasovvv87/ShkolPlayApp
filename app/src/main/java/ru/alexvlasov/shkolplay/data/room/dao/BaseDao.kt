package ru.alexvlasov.shkolplay.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

//Базовый интерфейс для реализации Dao

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<T>)
    @Update
    suspend fun update(item: T)
    @Delete
    suspend fun delete(item: T)
}