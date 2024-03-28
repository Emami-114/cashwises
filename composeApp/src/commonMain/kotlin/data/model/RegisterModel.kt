package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)
