package ru.alexvlasov.shkolplay.data.datastore
import kotlinx.coroutines.flow.Flow
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.domain.model.Theme

//Интерфейс для работы с DataStorage

interface DataStorage {

    fun selectedTheme() : Flow<String>
    suspend fun setSelectedTheme(theme: String)

    fun authToken() : Flow<String>
    suspend fun setAuthToken(token: String)
    suspend fun deleteToken()


    fun difficulty() : Flow<Difficulty>
    suspend fun setDifficulty(diff: Difficulty)

    fun cardTheme() : Flow<Theme>
    suspend fun setCardTheme(theme: Theme)
}