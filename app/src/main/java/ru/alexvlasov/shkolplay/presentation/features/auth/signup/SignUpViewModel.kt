package ru.alexvlasov.shkolplay.presentation.features.auth.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexvlasov.shkolplay.data.network.dto.RegisterData
import ru.alexvlasov.shkolplay.data.room.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {
    var name by mutableStateOf("")
    var surname by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordAgain by mutableStateOf("")
    var email by mutableStateOf("")
    var isTeacher by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)
    var passwordAgainVisible by mutableStateOf(false)
    var state: MutableState<SignUpState> = mutableStateOf(SignUpState.Default)

    //Запускает поток и создаёт Register Data
    fun signUp() {
        viewModelScope.launch {
            state.value = SignUpState.Loading
            userRepository.register(
                RegisterData(
                    name,
                    surname,
                    password,
                    email,
                    isTeacher
                ),
                onSuccess = { state.value = SignUpState.Success },
                onError = { state.value = SignUpState.Error(it) },
            )
        }
    }

    fun isUIEnabled() = state.value is SignUpState.Default || isError()
    fun isError() = state.value is SignUpState.Error
    fun setNewPassword(pass: String) {
        password = pass
        state.value = SignUpState.Default
    }
    fun setNewName(name: String) {
        this.name = name
        state.value = SignUpState.Default
    }
    fun setIsTeacher(isTeacher: Boolean) {
        this.isTeacher = isTeacher
        state.value = SignUpState.Default
    }
    fun setNewSurname(sname: String) {
        surname = sname
        state.value = SignUpState.Default
    }
    fun setAgainPassword(pass: String) {
        passwordAgain = pass
        state.value = SignUpState.Default
    }
    fun setNewEmail(mail: String) {
        email = mail
        state.value = SignUpState.Default
    }
    fun passwordsAreSimilar() = password == passwordAgain
}

//Изолированный класс
sealed class SignUpState {
    object Default : SignUpState()
    object Loading : SignUpState()
    class Error(val error: String = "") : SignUpState()
    object Success : SignUpState()
}