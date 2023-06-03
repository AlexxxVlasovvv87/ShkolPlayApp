package ru.alexvlasov.shkolplay.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.alexvlasov.shkolplay.data.datastore.Themes
import ru.alexvlasov.shkolplay.presentation.features.auth.AccountViewModel
import ru.alexvlasov.shkolplay.presentation.theme.ShkolPlayTheme

// Данная аннотация говорит Hilt, что сюда необходимо внедрять зависимости
@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Класс содержит данные, которые должны отображаться пользователю
            val viewModel: AccountViewModel = hiltViewModel()
            //Использование тем
            val theme = viewModel.theme.collectAsState(initial = Themes.SYSTEM.name).value
            val useDarkTheme = theme.let {
                when (it) {
                    Themes.LIGHT.name -> false
                    Themes.DARK.name -> true
                    else -> isSystemInDarkTheme()
                }
            }
            ShkolPlayTheme(useDarkTheme) {
                MainScreen(viewModel)
            }
        }
    }
}
