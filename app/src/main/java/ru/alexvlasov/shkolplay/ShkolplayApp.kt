package ru.alexvlasov.shkolplay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Включает Hilt в приложении и позволяет ему сгенерировать код зависимостей

@HiltAndroidApp
class ShkolplayApp : Application()