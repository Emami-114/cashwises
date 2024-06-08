package ui.account.auth.registration.viewModel

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.accept_data_protection_error
import cashwises.composeapp.generated.resources.auth_verification_required
import cashwises.composeapp.generated.resources.invalid_email_address_error
import cashwises.composeapp.generated.resources.password_confirm_not_match_error
import cashwises.composeapp.generated.resources.password_must_be_6characters_error
import cashwises.composeapp.generated.resources.username_required_error
import data.model.RegisterModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.account.auth.registration.RegistrationEvent
import ui.account.auth.registration.RegistrationState
import useCase.AuthUseCase
import utils.isValidEmail

class RegistrationViewModel : ViewModel(), KoinComponent {
    private val useCase: AuthUseCase by inject()
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    val toVerification: () -> Unit = {}
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
            _state.value.nameText.isEmpty() || _state.value.nameText.isBlank() -> {
                _state.update {
                    it.copy(nameError = "Invalid UserName")
                }
            }

            _state.value.emailText.isBlank() || _state.value.emailText.isEmpty() || !isValidEmail(
                _state.value.emailText
            ) -> {
                _state.update {
                    it.copy(emailError = "Invalid E-mail")
                }
            }


            _state.value.passwordText.length < 6 || _state.value.passwordText.isEmpty() || _state.value.passwordText.isBlank() -> {
                _state.update {
                    it.copy(passwordError = "Invalid password")
                }
            }

            _state.value.passwordConfirm != _state.value.passwordText -> {
                _state.update {
                    it.copy(passwordConfirmError = "Invalid passwordConfirm")
                }
            }

            !_state.value.acceptedDataProtection -> {
                _state.update {
                    it.copy(acceptedDataProtectionError = "Data privacy not accepted")
                }
            }

            else -> {
                if (_state.value.isLoading) return
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)

                    try {
                        val registerModel = RegisterModel(
                            name = _state.value.nameText,
                            email = _state.value.emailText,
                            password = _state.value.passwordText,
                            passwordConfirm = _state.value.passwordConfirm
                        )
                        useCase.register(registerModel) {
                            _state.update {
                                it.copy(
                                    isRegistrationSuccess = true,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                            toVerification()
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

    fun doVerification(onSuccess: () -> Unit) {
        when {
            _state.value.otpCode.isEmpty() || _state.value.otpCode.isBlank() || _state.value.otpCode.length <= 3 -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(verificationCodeError = getString(Res.string.auth_verification_required))
                    }
                }
            }

            else -> {
                if (_state.value.isLoading) return
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        useCase.verification(
                            email = _state.value.emailText,
                            code = _state.value.otpCode!!
                        ) {
                            _state.update {
                                it.copy(
                                    isVerificationSuccess = true,
                                    errorMessage = null,
                                    isLoading = false
                                )
                            }
                            onSuccess()
                        }
                    } catch (e: Exception) {
                        _state.update { it.copy(errorMessage = e.message) }
                        delay(4000)
                        _state.value = RegistrationState()
                    }
                }
            }
        }
    }

    fun doChangeOtpCode(value: String) {
        _state.update {
            it.copy(otpCode = value, verificationCodeError = null)
        }
    }

    private fun doAcceptedDataProtectChange(event: RegistrationEvent.OnAcceptedDataProtectChange) {
        _state.update {
            it.copy(
                acceptedDataProtection = event.value,
                acceptedDataProtectionError = null
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
                emailText = event.value.lowercase(),
                emailError = null
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
                passwordConfirmError = null
            )
        }
    }
}