package ui.category.viewModel

import data.repository.ImageUploadRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.CategoryModel
import domain.model.ImageModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.account.auth.registration.RegistrationEvent
import useCase.CategoryUseCase

class CategoryViewModel : ViewModel(), KoinComponent {
    private val useCase: CategoryUseCase by inject()
    private val imageRepository = ImageUploadRepository()
    private val _state = MutableStateFlow(CategoryState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CategoryState()
    )

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OnCreateCategory -> doCreateCategory(event)
            is CategoryEvent.OnUpdateCategory -> {}
            is CategoryEvent.OnTitleChange -> doTitleChange(event)
            is CategoryEvent.OnImageChange -> doThumbnailChange(event)
            is CategoryEvent.OnStatusChange -> doStatusChange(event)
            is CategoryEvent.OnPublishedChange -> doPublishedChange(event)
            is CategoryEvent.OnSubCategoriesChange -> doSubCategoriesChange(event)
            is CategoryEvent.OnDefaultState -> _state.value = CategoryState()
        }
    }

    fun getCategories() = viewModelScope.launch {
        _state.update {
            it.copy(
                categories = useCase.getCategories().categories
            )
        }
    }

    fun doChangeImageModel(imageModel: ImageModel?) {
        _state.update {
            it.copy(
                imageModel = imageModel
            )
        }
    }

    private fun doCreateCategory(event: CategoryEvent.OnCreateCategory) {
        when {
            _state.value.title.isEmpty() || _state.value.title.isBlank() -> {
                _state.update {
                    it.copy(titleErrorText = "Title is blank or empty")
                }
            }

            else -> {
                if (_state.value.isLoading) return
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    try {
                        if (_state.value.imageModel != null) {
                            useCase.uploadImage(
                                imageModel = _state.value.imageModel!!,
                                imagePaths = { imagePath ->
                                    _state.value =
                                        _state.value.copy(thumbnail = imagePath.firstOrNull())
                                    val category = CategoryModel(
                                        title = _state.value.title,
                                        thumbnail = _state.value.thumbnail,
                                        published = _state.value.published,
                                        status = _state.value.status,
                                        subCategories = _state.value.subCategories,
                                        userId = null
                                    )
                                    viewModelScope.launch {
                                        useCase.createCategory(category) {
                                            _state.update {
                                                it.copy(
                                                    createdSuccessfully = true,
                                                    isLoading = false,
                                                    errorText = null
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        } else {
                            val category = CategoryModel(
                                title = _state.value.title,
                                thumbnail = _state.value.thumbnail,
                                published = _state.value.published,
                                status = _state.value.status,
                                subCategories = _state.value.subCategories,
                                userId = null
                            )
                            useCase.createCategory(category) {
                                _state.update {
                                    it.copy(
                                        createdSuccessfully = true,
                                        isLoading = false,
                                        errorText = null
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                createdSuccessfully = false,
                                errorText = e.message
                            )
                        }
                        delay(4000)
                        _state.value = CategoryState()
                    }
                }
            }
        }
    }

    private fun doTitleChange(event: CategoryEvent.OnTitleChange) {
        _state.update {
            it.copy(
                title = event.value,
                titleErrorText = null
            )
        }
    }

    private fun doThumbnailChange(event: CategoryEvent.OnImageChange) {
        _state.update {
            it.copy(
                thumbnail = event.value
            )
        }
    }

    private fun doPublishedChange(event: CategoryEvent.OnPublishedChange) {
        _state.update {
            it.copy(
                published = event.value
            )
        }
    }

    private fun doStatusChange(event: CategoryEvent.OnStatusChange) {
        _state.update {
            it.copy(
                status = event.value
            )
        }
    }

    private fun doSubCategoriesChange(event: CategoryEvent.OnSubCategoriesChange) {
        _state.update {
            it.copy(
                subCategories = event.value
            )
        }
    }
}