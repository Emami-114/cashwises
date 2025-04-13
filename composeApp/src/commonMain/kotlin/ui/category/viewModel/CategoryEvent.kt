package ui.category.viewModel

import domain.model.CategoryModel

sealed interface CategoryEvent {
    data object OnCreateCategory : CategoryEvent
    data object OnUpdateCategory : CategoryEvent
    data object OnDeleteCategory: CategoryEvent
    data class OnTitleChange(val value: String) : CategoryEvent
    data class OnImageChange(val value: String) : CategoryEvent
    data class OnChangeMainCat(val value: CategoryModel?) : CategoryEvent
    data class OnPublishedChange(val value: Boolean) : CategoryEvent
    data object OnDefaultState : CategoryEvent
}