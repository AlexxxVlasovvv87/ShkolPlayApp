package ru.alexvlasov.shkolplay.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import ru.alexvlasov.shkolplay.R

//Класс навигации, переход по страницам, путь до экрана (указатели)

enum class NavigationItem(
    var route: String,
    @StringRes var title: Int,
    var icon: ImageVector,
) {
    Profile("profile", R.string.nav_profile, Icons.Rounded.Person),
    Login("auth", R.string.nav_login, Icons.Rounded.Login),
    ForgotPassword("forgot", R.string.nav_forgot_password, Icons.Rounded.Password),
    Register("register", R.string.nav_register, Icons.Rounded.PersonAdd),
    Splash("splash", R.string.nav_splash, Icons.Rounded.EmojiPeople),
    Games("menu", R.string.nav_menu, Icons.Rounded.Extension),
    Settings("settings", R.string.nav_settings, Icons.Rounded.Settings),
    MemoryGame("memory", R.string.nav_memory, Icons.Rounded.Memory),
    MathGame("math", R.string.nav_math, Icons.Rounded.Add),

    Groups("groups", R.string.nav_groups, Icons.Rounded.Groups),
    Students("students", R.string.nav_students, Icons.Rounded.Face),
    Teachers("teachers", R.string.nav_teachers, Icons.Rounded.School),
}

object NavigationInfo {
    val studentBottomBarItems = arrayListOf(
        NavigationItem.Settings,
        NavigationItem.Games,
        NavigationItem.Profile
    )
    val teacherBottomBarItems = arrayListOf(
        NavigationItem.Groups,
        NavigationItem.Students,
        NavigationItem.Teachers,
        NavigationItem.Profile
    )
}