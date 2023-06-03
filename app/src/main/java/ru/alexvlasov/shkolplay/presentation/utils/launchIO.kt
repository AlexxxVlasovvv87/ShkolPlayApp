package ru.alexvlasov.shkolplay.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// Запускает корутину в io контексте и выполняет что-либо, что ему передали

fun ViewModel.launchIO(toDo: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.IO) {
        toDo()
    }