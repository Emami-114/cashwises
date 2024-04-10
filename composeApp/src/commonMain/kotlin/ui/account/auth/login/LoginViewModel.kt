package ui.account.auth.login

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import useCase.AuthUseCase

class LoginViewModel : ViewModel(), KoinComponent {
    private val useCase: AuthUseCase by inject()
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
//    val state = _state.asStateFlow().stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000L),
//        LoginState()
//    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLogin -> doLogin(event)
            is LoginEvent.OnEmailChange -> doEmailChange(event)
            is LoginEvent.OnPasswordChange -> doPasswordChange(event)
            is LoginEvent.OnSetDefaultState -> _state.value = LoginState()
        }
    }


    private fun doLogin(event: LoginEvent.OnLogin) {
        when {
            _state.value.emailText.isBlank() || _state.value.emailText.isEmpty() -> {
                _state.update {
                    it.copy(
                        emailError = "Email is blank or empty"
                    )
                }
            }

            _state.value.passwordText.isBlank() || _state.value.passwordText.isBlank() -> {
                _state.update {
                    it.copy(
                        passwordError = "Email is blank or empty"
                    )
                }
            }

            else -> {
                if (_state.value.isLoading) return
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        useCase.login(
                            email = _state.value.emailText,
                            password = _state.value.passwordText
                        ) {
                            _state.update {
                                it.copy(
                                    isLoginSuccess = true,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isLoginSuccess = false,
                                isLoading = false,
                                errorMessage = e.message ?: "Some error"
                            )
                        }
                        delay(4000)
                        _state.value = LoginState()
                    }
                }
            }
        }
    }

    private fun doEmailChange(event: LoginEvent.OnEmailChange) {
        _state.update {
            it.copy(
                emailText = event.value,
                emailError = null
            )
        }
    }

    private fun doPasswordChange(event: LoginEvent.OnPasswordChange) {
        _state.update {
            it.copy(
                passwordText = event.value,
                passwordError = null
            )
        }
    }

}