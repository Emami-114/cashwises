package ui.account.auth.registration

data class RegistrationState(
    val isRegistrationSuccess: Boolean = false,
    val nameText: String = "",
    val emailText: String = "",
    val passwordText: String = "",
    val passwordConfirm: String = "",
    val acceptedDataProtection: Boolean = false,
    val acceptedDataProtectionError: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val passwordConfirmError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val verificationCodeError: String? = null,
    val isVerificationSuccess: Boolean = false,
    val otpCode: String = ""
)
