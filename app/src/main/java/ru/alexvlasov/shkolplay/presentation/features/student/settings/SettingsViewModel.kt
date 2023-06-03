package ru.alexvlasov.shkolplay.presentation.features.student.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.alexvlasov.shkolplay.data.datastore.DataStorage
import ru.alexvlasov.shkolplay.domain.model.Difficulty
import ru.alexvlasov.shkolplay.domain.model.Theme
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val dataStore: DataStorage
) : ViewModel() {
    val _theme = MutableStateFlow(Theme.RANDOM)
    val theme = _theme.asStateFlow()
    val _difficulty = MutableStateFlow(Difficulty.Easy)
    val difficulty = _difficulty.asStateFlow()
    init {
        launchIO { dataStore.difficulty().collect { _difficulty.value = it } }
        launchIO { dataStore.cardTheme().collect { _theme.value = it } }
    }
    fun setDiff(diff: Difficulty) = launchIO { dataStore.setDifficulty(diff) }
    fun setTheme(theme: Theme) = launchIO { dataStore.setCardTheme(theme) }
}