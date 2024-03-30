package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesModel(
    val categories: List<CategoryModel>
)

@Serializable
data class CategoryModel(
    val id: String? = null,
    val title: String? = null,
    val thumbnail: String? = null,
    val published: Boolean? = null,
    val userId: String? = null,
    val status: CategoryStatus? = null,
    @SerialName("sub_categories")
    val subCategories: List<String>? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null
)

@Serializable
enum class CategoryStatus {
    @SerialName("main")
    MAIN,

    @SerialName("sub")
    SUB
}