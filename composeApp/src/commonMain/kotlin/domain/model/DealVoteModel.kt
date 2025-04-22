package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealVoteModel(
    val id: String? = null,
    val dealId: String,
    val userId: String,
)
