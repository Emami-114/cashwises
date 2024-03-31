package ui.category.viewModel

import domain.model.CategoryModel
import domain.model.CategoryStatus
import domain.model.ImageModel

data class CategoryState(
    val categories: List<CategoryModel> = listOf(),
    val title: String? = null,
    val thumbnail: String? = null,
    val published: Boolean? = false,
    val userId: String? = null,
    val mainId: String? = null,
    val errorText: String? = null,
    val isLoading: Boolean = false,
    val createdSuccessfully: Boolean = false,
    val titleErrorText: String? = null,
    val imageModel: ImageModel? = null,
    val status: CategoryStatus? = null,
    val updateSuccessfully: Boolean = false,
    val selectedCategory: CategoryModel? = null
)
