package ui.category.viewModel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.CategoryModel
import domain.model.CategoryStatus
import domain.model.ImageModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import useCase.CategoryUseCase

class CategoryViewModel : ViewModel(), KoinComponent {
    private val useCase: CategoryUseCase by inject()
    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OnCreateCategory -> doCreateCategory(event)
            is CategoryEvent.OnUpdateCategory -> doUpdateCategory(event)
            is CategoryEvent.OnDeleteCategory -> {}
            is CategoryEvent.OnTitleChange -> doTitleChange(event)
            is CategoryEvent.OnImageChange -> doThumbnailChange(event)
            is CategoryEvent.OnStatusChange -> doStatusChange(event)
            is CategoryEvent.OnPublishedChange -> doPublishedChange(event)
            is CategoryEvent.OnSelectedCatChange -> doMainIdChange(event)
            is CategoryEvent.OnDefaultState -> _state.value = CategoryState()
        }
    }

    private fun doUpdateCategory(event: CategoryEvent.OnUpdateCategory) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                if (_state.value.imageModel != null) {
                    useCase.uploadImage(
                        imageModel = _state.value.imageModel!!,
                        imagePaths = { imagePath ->
                            _state.value =
                                _state.value.copy(thumbnail = imagePath)
                            val category = CategoryModel(
                                title = _state.value.title,
                                thumbnail = _state.value.thumbnail,
                                published = _state.value.published,
                                status = _state.value.status,
                                mainId = _state.value.selectedCategory?.id,
                                userId = null
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
                        published = _state.value.published,
                        status = _state.value.status,
                        mainId = _state.value.selectedCategory?.id,
                        userId = null
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
        _state.update { it.copy(isLoading = true) }
        try {
            _state.update {
                it.copy(
                    categories = useCase.getCategories().categories,
                    isLoading = false
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorText = e.message,
                    isLoading = false
                )
            }
            delay(4000)
            onEvent(CategoryEvent.OnDefaultState)
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
                            useCase.uploadImage(
                                imageModel = _state.value.imageModel!!,
                                imagePaths = { imagePath ->
                                    _state.value =
                                        _state.value.copy(thumbnail = imagePath)
                                    val category = CategoryModel(
                                        title = _state.value.title,
                                        thumbnail = _state.value.thumbnail,
                                        published = _state.value.published,
                                        status = if (_state.value.selectedCategory != null) CategoryStatus.SUB else CategoryStatus.MAIN,
                                        mainId = if (_state.value.selectedCategory != null) _state.value.selectedCategory!!.id else _state.value.mainId,
                                        userId = null
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
                                published = _state.value.published,
                                status = if (_state.value.selectedCategory != null) CategoryStatus.SUB else CategoryStatus.MAIN,
                                mainId = if (_state.value.selectedCategory != null) _state.value.selectedCategory!!.id else _state.value.mainId,
                                userId = null
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

    private fun doMainIdChange(event: CategoryEvent.OnSelectedCatChange) {
        _state.update {
            it.copy(
                selectedCategory = event.value
            )
        }
    }
}