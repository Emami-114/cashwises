package ui.account.auth.registration

data class RegistrationState(
    val isRegistrationSuccess: Boolean = false,
    val nameText: String = "",
    val emailText: String = "",
    val passwordText: String = "",
    val passwordConfirm: String = "",
    val acceptedDataProtection: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
