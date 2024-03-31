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
    @SerialName("user_id")
    val userId: String? = null,
    val status: CategoryStatus? = null,
    @SerialName("main_id")
    val mainId: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
enum class CategoryStatus {
    @SerialName("main")
    MAIN,
    @SerialName("sub")
    SUB
}