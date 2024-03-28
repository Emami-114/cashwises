package ui.account.auth.login

sealed interface LoginEvent {
    data object OnLogin : LoginEvent
    data class OnEmailChange(val value: String) : LoginEvent
    data class OnPasswordChange(val value: String) : LoginEvent
    data object OnSetDefaultState : LoginEvent
}