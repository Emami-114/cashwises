package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealVoteModel(
    val dealId: String,
    val userId: String,
    val value: Int,
)
