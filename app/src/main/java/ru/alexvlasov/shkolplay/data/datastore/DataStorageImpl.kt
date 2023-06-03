package ru.alexvlasov.shkolplay.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.domain.model.Theme
import javax.inject.Inject
import javax.inject.Singleton

//Реализация интерфейса interface implementation

//Создание словаря с названием DataStorage
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_Storage")

@Singleton
class DataStorageImpl @Inject constructor(@ApplicationContext context: Context) : DataStorage {

    private val dataStore = context.dataStore

    private object PreferenceKeys{
        val SELECTED_THEME = stringPreferencesKey("selected_theme")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val DIFFICULTY = stringPreferencesKey("difficulty")
        val CARD_THEME = stringPreferencesKey("card_theme")
    }

    //Системные либо записанные
    override fun selectedTheme() = dataStore.data.map {
        //получаем значение или задаём по умолчанию
        it[PreferenceKeys.SELECTED_THEME] ?: Themes.SYSTEM.name
    }


    override suspend fun setSelectedTheme(theme: String) {
        dataStore.edit {
            it[PreferenceKeys.SELECTED_THEME] = theme
        }
    }

    override fun authToken(): Flow<String> = dataStore.data.map {
        it[PreferenceKeys.AUTH_TOKEN] ?: ""
    }
    override suspend fun setAuthToken(token: String) {
        dataStore.edit {
            it[PreferenceKeys.AUTH_TOKEN] = token
        }
    }

    override suspend fun deleteToken() {
        dataStore.edit {
            it[PreferenceKeys.AUTH_TOKEN] = ""
        }
    }


    override fun difficulty() = dataStore.data.map {
        Difficulty.valueOf(it[PreferenceKeys.DIFFICULTY] ?: Difficulty.Easy.name)
    }


    override suspend fun setDifficulty(diff: Difficulty) {
        dataStore.edit {
            it[PreferenceKeys.DIFFICULTY] = diff.name
        }
    }


    override fun cardTheme() = dataStore.data.map {
        Theme.valueOf(it[PreferenceKeys.CARD_THEME] ?: Theme.RANDOM.name)
    }


    override suspend fun setCardTheme(theme: Theme) {
        dataStore.edit {
            it[PreferenceKeys.CARD_THEME] = theme.name
        }
    }
}