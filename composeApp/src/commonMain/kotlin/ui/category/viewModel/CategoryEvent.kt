package ui.category.viewModel

import domain.model.CategoryStatus

sealed interface CategoryEvent {
    data object OnCreateCategory : CategoryEvent
    data object OnUpdateCategory : CategoryEvent
    data class OnTitleChange(val value: String) : CategoryEvent
    data class OnImageChange(val value: String) : CategoryEvent
    data class OnSubCategoriesChange(val value: List<String>) : CategoryEvent
    data class OnStatusChange(val value: CategoryStatus) : CategoryEvent
    data class OnPublishedChange(val value: Boolean) : CategoryEvent
    data object OnDefaultState : CategoryEvent
}