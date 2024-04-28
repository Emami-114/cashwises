package data.model

import kotlinx.serialization.Serializable

@Serializable
data class VerificationModel(
    val email: String,
    val code: String
)
