package domain.model

import kotlinx.serialization.SerialName

data class CategoryModel(
    val id: String,
    val title: String,
    val thumbnail: String,
    val published: Boolean,
    @SerialName("created_at")
    val userId: String? = null,
    val subCategories: List<String>? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)