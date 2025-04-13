package ui.category.viewModel

import domain.model.CategoryModel
import domain.model.ImageModel

data class CategoryState(
    val categories: List<CategoryModel> = listOf(),
    val title: String? = null,
    val thumbnail: String? = null,
    val isPublic: Boolean = true,
    val parentCategoryId: String? = null,
    val errorText: String? = null,
    val isLoading: Boolean = false,
    val createdSuccessfully: Boolean = false,
    val titleErrorText: String? = null,
    val imageModel: ImageModel? = null,
    val updateSuccessfully: Boolean = false,
    val selectMainCategory: CategoryModel? = null,
    var isUpdate: Boolean = false
)
