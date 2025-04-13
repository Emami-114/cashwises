package ui.category.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.CategoryModel
import domain.model.ImageModel
import domain.repository.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.deals.ViewModel.DealsState
import useCase.CategoryUseCase
import useCase.ImageUploadUseCase

class CategoryViewModel : ViewModel(), KoinComponent {
    private val useCase: CategoryUseCase by inject()
    private val imageUseCase: ImageUploadUseCase by inject()
    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        initialValue = CategoryState()
    )

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OnCreateCategory -> doCreateCategory(event)
            is CategoryEvent.OnUpdateCategory -> doUpdateCategory(event)
            is CategoryEvent.OnDeleteCategory -> {}
            is CategoryEvent.OnTitleChange -> doTitleChange(event)
            is CategoryEvent.OnImageChange -> doThumbnailChange(event)
            is CategoryEvent.OnPublishedChange -> doPublishedChange(event)
            is CategoryEvent.OnChangeMainCat -> doChangeMainCat(event)
            is CategoryEvent.OnDefaultState -> _state.value = CategoryState()
        }
    }

    private fun doUpdateCategory(event: CategoryEvent.OnUpdateCategory) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                if (_state.value.imageModel != null) {
                    imageUseCase.uploadThumbnailImage(
                        image = _state.value.imageModel!!,
                        imagePath = { imagePath ->
                            _state.value =
                                _state.value.copy(thumbnail = imagePath)
                            val category = CategoryModel(
                                title = _state.value.title,
                                thumbnail = _state.value.thumbnail,
                                isPublic = _state.value.isPublic,
                                parentCategoryId = _state.value.selectMainCategory?.id,
                            )
                            viewModelScope.launch {
                                useCase.updateCategory(category) {
                                    _state.update {
                                        it.copy(
                                            updateSuccessfully = true,
                                            errorText = null,
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
                        isPublic = _state.value.isPublic,
                        parentCategoryId = _state.value.selectMainCategory?.id,
                    )
                    useCase.createCategory(category) {
                        _state.update {
                            it.copy(
                                updateSuccessfully = true,
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
                onEvent(CategoryEvent.OnDefaultState)
            }
        }
    }

    fun getCategories() = viewModelScope.launch {
        try {
            useCase.getCategories().collectLatest { status ->
                when (status) {
                    is Result.Loading -> _state.value.copy(isLoading = true, errorText = null)
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                categories = status.data ?: listOf(),
                                isLoading = false,
                                errorText = null
                            )
                        }
                        println("Category State: ${_state.value.categories}")
                    }

                    is Result.Error -> _state.value.copy(
                        errorText = getString(status.error?.message!!),
                        isLoading = false
                    )
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorText = e.message
                )
            }
            delay(4000)
            _state.value = CategoryState()
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
            _state.value.title.isNullOrEmpty() || _state.value.title.isNullOrBlank() -> {
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
                            imageUseCase.uploadCategoryImage(
                                image = _state.value.imageModel!!,
                                imagePath = { imagePath ->
                                    _state.value =
                                        _state.value.copy(thumbnail = imagePath)
                                    val category = CategoryModel(
                                        title = _state.value.title,
                                        thumbnail = _state.value.thumbnail,
                                        isPublic = _state.value.isPublic,
                                        parentCategoryId = if (_state.value.selectMainCategory != null) _state.value.selectMainCategory!!.id else null,
                                    )
                                    viewModelScope.launch {
                                        useCase.createCategory(category) {
                                            _state.value = CategoryState()
                                        }
                                    }
                                }
                            )
                        } else {
                            val category = CategoryModel(
                                title = _state.value.title,
                                thumbnail = _state.value.thumbnail,
                                isPublic = _state.value.isPublic,
                                parentCategoryId = if (_state.value.selectMainCategory != null) _state.value.selectMainCategory!!.id else null,
                            )
                            useCase.createCategory(category) {
                                _state.value = CategoryState()
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
                isPublic = event.value
            )
        }
    }

    private fun doChangeMainCat(event: CategoryEvent.OnChangeMainCat) {
        _state.update {
            it.copy(
                selectMainCategory = event.value
            )
        }
    }
}