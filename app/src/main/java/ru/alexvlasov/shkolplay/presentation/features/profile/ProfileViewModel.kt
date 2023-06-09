package ru.alexvlasov.shkolplay.presentation.features.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.alexvlasov.shkolplay.data.network.dto.UpdatePasswordData
import ru.alexvlasov.shkolplay.data.network.dto.UserData
import ru.alexvlasov.shkolplay.data.room.repositories.UserRepository
import ru.alexvlasov.shkolplay.domain.model.User
import ru.alexvlasov.shkolplay.presentation.utils.launchIO
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val user: MutableState<User?> = mutableStateOf(null)
    var state: MutableState<ProfileState> = mutableStateOf(ProfileState.Default)
    var isPasswordChanging by mutableStateOf(false)
    var changing by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)
    var newPasswordVisible by mutableStateOf(false)
    var passwordText by mutableStateOf("")
    var nameText by mutableStateOf("")
    var surnameText by mutableStateOf("")
    var teacherText by mutableStateOf("")
    var newPasswordText by mutableStateOf("")
    var emailText by mutableStateOf("")

    //Поток ввода вывода данных (отображение из локалки)
    fun fetch() {
        launchIO {
            repository.get().distinctUntilChanged().collect { u ->
                user.value = u
                u?.let {
                    emailText = u.email
                    surnameText = u.surname
                    nameText = u.name
                    teacherText = u.role.fullName
                }
            }
        }
        launchIO { repository.fetchMe() }
    }

    fun reset() {
        nameText = user.value?.name ?: ""
        surnameText = user.value?.surname ?: ""
    }
    //Обновление пароля
    fun updatePassword(onFinish: suspend () -> Unit) = launchIO {
        state.value = ProfileState.Loading
        repository.update(
            UpdatePasswordData(
            passwordText, newPasswordText
        ),
        onSuccess = {
            state.value = ProfileState.Success("Пароль обновлен")
            passwordText = ""
            newPasswordText = ""
            isPasswordChanging = false
            onFinish()
        },
        onError = {
            state.value = ProfileState.Error(it)
            onFinish()
        })
    }
    //Обновление данных
    fun update(onFinish: suspend () -> Unit) = launchIO {
        state.value = ProfileState.Loading
        repository.update(
            UserData(nameText, surnameText),
            onSuccess = {
                state.value = ProfileState.Success("Сохранение произведено")
                changing = false
                onFinish()
            },
            onError = {
                state.value = ProfileState.Error(it)
                onFinish()
            })
    }


    //Проверка состояния не loading
    fun isUIEnabled() = state.value is ProfileState.Default || state.value is ProfileState.Success || isError()
    fun isError() = state.value is ProfileState.Error
    fun snackBarText() = when(state.value) {
        is ProfileState.Success -> (state.value as ProfileState.Success).text
        is ProfileState.Error -> (state.value as ProfileState.Error).error
        else -> ""
    }
    fun setPassword(pass: String) {
        passwordText = pass
        state.value = ProfileState.Default
    }
    fun setNewPassword(pass: String) {
        newPasswordText = pass
        state.value = ProfileState.Default
    }
}

sealed class ProfileState {
    object Default : ProfileState()
    object Loading : ProfileState()
    class Error(val error: String = "") : ProfileState()
    class Success(val text: String = "") : ProfileState()
}

