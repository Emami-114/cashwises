package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String? = null,
    val role: UserRole,
    val photo: String,
    val verified: Boolean = false,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

enum class UserRole {
    @SerialName("admin")
    ADMIN,

    @SerialName("creator")
    CREATOR,

    @SerialName("customer")
    CUSTOMER

}