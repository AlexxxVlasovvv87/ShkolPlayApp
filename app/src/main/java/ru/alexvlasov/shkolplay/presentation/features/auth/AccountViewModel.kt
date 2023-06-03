package ru.alexvlasov.shkolplay.presentation.features.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.data.datastore.Themes
import ru.alexvlasov.shkolplay.data.room.repositories.AchievementRepository
import ru.alexvlasov.shkolplay.data.room.repositories.UserRepository
import ru.alexvlasov.shkolplay.domain.model.User
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import javax.inject.Inject

// Хранит тему, фетчит юзера (передавать данные пользователя и темы на экраны)
// Зависимости будут приходить через конструктор (Inject)
@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: UserRepository,
    val achievementRepository: AchievementRepository,
    val dataStorage: DataStorage
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    val _theme = MutableStateFlow(Themes.SYSTEM.name)
    val theme = _theme.asStateFlow()
    fun fetchUser() {
        fetchTheme()
        launchIO { repository.get().distinctUntilChanged().collect { _currentUser.value = it } }
        launchIO { repository.fetchMe() }
    }
    fun fetchTheme() = launchIO { dataStorage.selectedTheme().collect { _theme.value = it }}
    fun setTheme(theme: Themes) = launchIO {
        dataStorage.setSelectedTheme(theme.name)
    }

    fun logOut() = launchIO { repository.logout() }
}