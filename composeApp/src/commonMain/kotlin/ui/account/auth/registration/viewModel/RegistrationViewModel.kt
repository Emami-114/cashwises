package ui.account.auth.registration.viewModel

import data.model.RegisterModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.account.auth.registration.RegistrationEvent
import ui.account.auth.registration.RegistrationState
import useCase.AuthUseCase

class RegistrationViewModel : ViewModel(), KoinComponent {
    private val useCase: AuthUseCase by inject()

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), RegistrationState()
    )

    fun onRegisterEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnRegistration -> doRegistration(event)
            is RegistrationEvent.OnUserNameChange -> doUserNameChange(event)
            is RegistrationEvent.OnEmailChange -> doEmailChange(event)
            is RegistrationEvent.OnPasswordChange -> doPasswordChange(event)
            is RegistrationEvent.OnPasswordConfirmChange -> doPasswordConfirmChange(event)
            is RegistrationEvent.OnAcceptedDataProtectChange -> doAcceptedDataProtectChange(event)
            is RegistrationEvent.OnSetDefault -> _state.value = RegistrationState()
        }
    }

    private fun doRegistration(event: RegistrationEvent.OnRegistration) {
        when {
            _state.value.emailText.isBlank() || _state.value.emailText.isEmpty() -> {
                _state.update {
                    it.copy(emailError = "Email is blank or empty")
                }
            }

            else -> {
                if (_state.value.isLoading) return
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)

                    try {
                        val registeModel = RegisterModel(
                            name = _state.value.nameText,
                            email = _state.value.emailText,
                            password = _state.value.passwordText,
                            passwordConfirm = _state.value.passwordConfirm
                        )
                        useCase.register(registeModel) {
                            _state.update {
                                it.copy(
                                    isRegistrationSuccess = true,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }

                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isRegistrationSuccess = false,
                                isLoading = false,
                                errorMessage = e.message
                            )
                        }
                        delay(4000)
                        _state.value = RegistrationState()
                    }
                }
            }
        }
    }

    private fun doAcceptedDataProtectChange(event: RegistrationEvent.OnAcceptedDataProtectChange) {
        _state.update {
            it.copy(
                acceptedDataProtection = event.value
            )
        }
    }

    private fun doUserNameChange(event: RegistrationEvent.OnUserNameChange) {
        _state.update {
            it.copy(
                nameText = event.value,
                nameError = null
            )
        }
    }

    private fun doEmailChange(event: RegistrationEvent.OnEmailChange) {
        _state.update {
            it.copy(
                emailText = event.value,
                passwordError = null
            )
        }
    }

    private fun doPasswordChange(event: RegistrationEvent.OnPasswordChange) {
        _state.update {
            it.copy(
                passwordText = event.value,
                passwordError = null
            )
        }
    }

    private fun doPasswordConfirmChange(event: RegistrationEvent.OnPasswordConfirmChange) {
        _state.update {
            it.copy(
                passwordConfirm = event.value,
                passwordError = null
            )
        }
    }

}