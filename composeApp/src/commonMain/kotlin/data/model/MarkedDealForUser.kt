package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarkedDealForUser(
    @SerialName("user_id")
    val userId: String,
    @SerialName("deal_id")
    val dealId: String,
)
