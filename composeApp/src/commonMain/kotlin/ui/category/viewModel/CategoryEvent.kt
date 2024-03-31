package ui.category.viewModel

import domain.model.CategoryModel
import domain.model.CategoryStatus

sealed interface CategoryEvent {
    data object OnCreateCategory : CategoryEvent
    data object OnUpdateCategory : CategoryEvent
    data object OnDeleteCategory: CategoryEvent
    data class OnTitleChange(val value: String) : CategoryEvent
    data class OnImageChange(val value: String) : CategoryEvent
    data class OnSelectedCatChange(val value: CategoryModel?) : CategoryEvent
    data class OnStatusChange(val value: CategoryStatus) : CategoryEvent
    data class OnPublishedChange(val value: Boolean) : CategoryEvent
    data object OnDefaultState : CategoryEvent
}