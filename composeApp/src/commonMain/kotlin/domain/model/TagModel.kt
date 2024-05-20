package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TagModel(
    val id: String? = null,
    val title: String,
)