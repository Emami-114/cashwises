package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarkedDealForUser(
    val userId: String,
    val dealId: String,
)
