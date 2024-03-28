package ui.account.auth.registration

sealed interface RegistrationEvent {
    data object OnRegistration : RegistrationEvent
    data class OnUserNameChange(val value: String) : RegistrationEvent
    data class OnEmailChange(val value: String) : RegistrationEvent
    data class OnPasswordChange(val value: String) : RegistrationEvent
    data class OnPasswordConfirmChange(val value: String) : RegistrationEvent
    data class OnAcceptedDataProtectChange(val value: Boolean) : RegistrationEvent
    data object OnSetDefault : RegistrationEvent
}