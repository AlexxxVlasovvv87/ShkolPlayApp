package ru.alexvlasov.shkolplay.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import ru.alexvlasov.shkolplay.presentation.base.BottomNavigationBar
import ru.alexvlasov.shkolplay.presentation.base.ConnectionState
import ru.alexvlasov.shkolplay.presentation.base.connectivityState
import ru.alexvlasov.shkolplay.presentation.features.auth.AccountViewModel
import ru.alexvlasov.shkolplay.presentation.features.auth.signin.SignInScreen
import ru.alexvlasov.shkolplay.presentation.navigation.NavigationItem
import ru.alexvlasov.shkolplay.presentation.features.auth.signup.SignUpScreen
import ru.alexvlasov.shkolplay.presentation.features.profile.ProfileScreen
import ru.alexvlasov.shkolplay.presentation.features.student.math.MathScreen
import ru.alexvlasov.shkolplay.presentation.features.student.memory.GameScreen
import ru.alexvlasov.shkolplay.presentation.features.student.menu.MenuScreen
import ru.alexvlasov.shkolplay.presentation.features.student.settings.SettingsScreen
import ru.alexvlasov.shkolplay.presentation.features.teacher.groups.GroupsScreen
import ru.alexvlasov.shkolplay.presentation.features.teacher.rate.RatesScreen
import ru.alexvlasov.shkolplay.presentation.features.teacher.teacherslist.TeachersScreen
import java.util.*

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class
)
@Composable
fun MainScreen(
    viewModel: AccountViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    var animationDisplayed by remember { mutableStateOf(true) }
    val user = viewModel.currentUser.collectAsState().value

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    LaunchedEffect(true){
        viewModel.fetchUser()
        delay(3000)
        animationDisplayed = false
    }
    val startDestination = when {
        animationDisplayed -> NavigationItem.Splash
        user == null -> NavigationItem.Login
        else -> NavigationItem.Profile
    }
    if (isConnected)
    Scaffold( //позвоняет настраивать навигацию
        bottomBar = {
            if (!animationDisplayed) {
                Column {
                    if (startDestination != NavigationItem.Login)
                        BottomNavigationBar(navController, user?.role)
                }
            }
        }
    ) {
        NavHost( //Класс, который занимается навигацией
            navController = navController,
            startDestination = startDestination.route,
            modifier = Modifier.padding(it) //Реализатор указателей
        ) {
            composable(NavigationItem.Splash.route) {
                Splash()
            }
            composable(NavigationItem.Profile.route) {
                ProfileScreen(navController)
            }
            composable(NavigationItem.Login.route) {
                SignInScreen(navController)
            }
            composable(NavigationItem.Register.route) {
                SignUpScreen(navController)
            }
            composable(NavigationItem.Games.route) {
                MenuScreen(navController)
            }
            composable(NavigationItem.MemoryGame.route) {
                GameScreen(navController)
            }
            composable(NavigationItem.MathGame.route) {
                MathScreen(navController)
            }
            composable(NavigationItem.Settings.route) {
                SettingsScreen(navController)
            }
            composable(NavigationItem.Groups.route) {
                GroupsScreen(navController)
            }
            composable(NavigationItem.Students.route,
            ) {
                RatesScreen(navController, null)
            }
            composable(NavigationItem.Teachers.route,
            ) {
                TeachersScreen(navController)
            }
            composable("${NavigationItem.Students.route}/{groupId}") { entry ->
                var uuid: UUID? = null
                entry.arguments?.getString("groupId")?.let {
                    uuid = UUID.fromString(it)
                }
                RatesScreen(navController, uuid)
            }
        }
    }
    else {
        WaitConnectionSplash()
    }
}



