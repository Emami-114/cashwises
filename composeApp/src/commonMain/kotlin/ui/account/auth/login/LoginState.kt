package ui.account.auth.login

import com.seiko.imageloader.model.ImageAction

data class LoginState(
    val isLoginSuccess: Boolean = false,
    val emailText: String = "",
    val passwordText: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
