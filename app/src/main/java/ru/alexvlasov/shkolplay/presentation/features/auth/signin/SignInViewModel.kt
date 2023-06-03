package ru.alexvlasov.shkolplay.presentation.features.auth.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.alexvlasov.shkolplay.data.network.dto.LoginData
import ru.alexvlasov.shkolplay.data.room.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var state: MutableState<SignInState> = mutableStateOf(SignInState.Default())

    fun signIn() {
        viewModelScope.launch {
            state.value = SignInState.Loading()
            userRepository.login(
                LoginData(email, password),
                onSuccess = { state.value = SignInState.Success() },
                onError = { state.value = SignInState.Error(it) },
            )
        }
    }

    fun isUIEnabled() = state.value is SignInState.Default || isError()
    fun isError() = state.value is SignInState.Error
    fun setNewPassword(pass: String) {
        password = pass
        state.value = SignInState.Default()
    }
    fun setNewEmail(mail: String) {
        email = mail
        state.value = SignInState.Default()
    }
}

sealed class SignInState() {
    class Default : SignInState()
    class Loading : SignInState()
    class Error(val error: String = "") : SignInState()
    class Success : SignInState()
}