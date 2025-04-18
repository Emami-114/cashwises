package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val userId: String,
    var name: String,
    val email: String,
    var role: UserRole,
    val photoUrl: String? = null,
    val verified: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

enum class UserRole {
    @SerialName("Admin")
    ADMIN,
    @SerialName("Creator")
    CREATOR,
    @SerialName("Customer")
    CUSTOMER
}