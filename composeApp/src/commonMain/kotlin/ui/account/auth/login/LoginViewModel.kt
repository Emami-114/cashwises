package ui.account.auth.login

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.invalid_email_address_error
import cashwises.composeapp.generated.resources.login_failed_error
import cashwises.composeapp.generated.resources.password_required_error
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import useCase.AuthUseCase
import utils.isValidEmail

class LoginViewModel : ViewModel(), KoinComponent {
    private val useCase: AuthUseCase by inject()
    private val _state = MutableStateFlow(LoginState())

    //    val state = _state.asStateFlow().stateIn(
//        viewModelScope,
//        SharingStarted.Lazily,
//        LoginState()
//    )
    val state = _state.asStateFlow()

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
            _state.value.emailText.isBlank() || _state.value.emailText.isEmpty() || !isValidEmail(
                _state.value.emailText
            ) -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            emailError = getString(Res.string.invalid_email_address_error)
                        )
                    }
                }
            }

            _state.value.passwordText.isBlank() || _state.value.passwordText.isBlank() -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            passwordError = getString(Res.string.password_required_error)
                        )
                    }
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
                        viewModelScope.launch {
                            _state.update {
                                it.copy(
                                    isLoginSuccess = false,
                                    isLoading = false,
                                    errorMessage = getString(Res.string.login_failed_error)
                                )
                            }
                            delay(4000)
                            _state.value = LoginState()
                        }
                    }
                }
            }
        }
    }

    private fun doEmailChange(event: LoginEvent.OnEmailChange) {
        _state.update {
            it.copy(
                emailText = event.value.lowercase(),
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