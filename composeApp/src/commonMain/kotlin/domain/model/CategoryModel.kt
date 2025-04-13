package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: String? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val isPublic: Boolean = true,
    val parentCategoryId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val isMainCategory: Boolean? = null,
)