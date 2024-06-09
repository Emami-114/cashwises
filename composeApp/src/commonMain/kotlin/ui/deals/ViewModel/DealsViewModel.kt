package ui.deals.ViewModel

import data.model.DealsQuery
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.DealModel
import domain.model.ImageModel
import domain.repository.Results
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
import useCase.CategoryUseCase
import useCase.DealsUseCase
import useCase.TagsUseCase

class DealsViewModel : ViewModel(), KoinComponent {
    private val useCase: DealsUseCase by inject()
    private val tagsUseCase: TagsUseCase by inject()
    private val _state = MutableStateFlow(DealsState())
    val state = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            DealsState()
        )
    private val categoriesUseCase: CategoryUseCase by inject()

    init {
        getDeals()
    }

    fun doChangeSelectedDeal(dealModel: DealModel?) {
        _state.update {
            it.copy(
                selectedDeal = dealModel
            )
        }
    }

    fun doGetSingleDeal(id: String, success: (DealModel?) -> Unit) {
        viewModelScope.launch {
            println("test deal view model")
            try {
                useCase.getSingleDeal(id).collectLatest { status ->
                    when (status) {
                        is Results.Loading -> {}
                        is Results.Success -> success(status.data)
                        is Results.Error -> {}
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun onEvent(event: DealEvent) {
        when (event) {
            is DealEvent.OnTitleChange -> doChangeTitle(event)
            is DealEvent.OnDescriptionChange -> doChangeDescription(event)
            is DealEvent.OnCategoryChange -> doChangeCategory(event)
            is DealEvent.OnIsFreeChange -> doChangeIsFree(event)
            is DealEvent.OnPriceChange -> doChangePrice(event)
            is DealEvent.OnOfferPriceChange -> doChangeOfferPrice(event)
            is DealEvent.OnPublishedChange -> doChangePublished(event)
            is DealEvent.OnExpirationDateChange -> doChangeExpirationDate(event)
            is DealEvent.OnProviderChange -> doChangeProvider(event)
            is DealEvent.OnProviderUrlChange -> doChangeProviderUrl(event)
            is DealEvent.OnThumbnailChange -> doChangeThumbnail(event)
            is DealEvent.OnImagesChange -> doChangeImages(event)
            is DealEvent.OnVideoUrlChange -> doChangeVideoUrl(event)
            is DealEvent.OnAction -> doCreateDeal(event)
            is DealEvent.OnCouponCodeChange -> doChangeCouponCode(event)
            is DealEvent.OnTagsChange -> doChangeTags(event)
            is DealEvent.OnShippingCostChange -> doChangeShippingCosts(event)
            is DealEvent.OnSetDefaultState -> _state.value = DealsState()
        }
    }

    fun doChangeThumbnail(image: ImageModel?) {
        _state.update {
            it.copy(
                thumbnailByte = image,
                imagesByte = listOf(image!!)
            )
        }
    }

    fun doChangeImages(image: List<ImageModel>?) {
        _state.update {
            it.copy(imagesByte = _state.value.imagesByte?.plus(image!!))
        }
    }

    private fun doCreateDeal(event: DealEvent.OnAction) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            if (_state.value.thumbnailByte != null) {
                useCase.uploadImage(_state.value.thumbnailByte!!) { path ->
                    _state.update {
                        it.copy(thumbnail = path)
                    }
                }
            }
            if (_state.value.imagesByte != null) {
                useCase.uploadImages(_state.value.imagesByte!!) { imagesPath ->
                    _state.update {
                        it.copy(
                            images = imagesPath
                        )
                    }
                }
            }
            val deal = DealModel(
                title = _state.value.title,
                description = _state.value.description,
                categories = _state.value.category,
                isFree = _state.value.isFree,
                couponCode = _state.value.couponCode,
                tags = _state.value.selectedTags,
                shippingCosts = _state.value.shippingCosts,
                price = _state.value.price?.toDouble(),
                offerPrice = _state.value.offerPrice?.toDouble(),
                published = _state.value.published,
                expirationDate = _state.value.expirationDate,
                provider = _state.value.provider,
                providerUrl = _state.value.providerUrl,
                thumbnail = _state.value.thumbnail,
                images = _state.value.images,
                userId = _state.value.userId,
                videoUrl = _state.value.videoUrl,
            )
            try {
                useCase.addDeals(dealModel = deal) {
                    _state.update {
                        it.copy(
                            dealCreatedSuccess = true,
                            isLoading = false,
                            error = null
                        )
                    }
                    println("Deals mit image $deal")
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = e.message,
                        isLoading = false,
                    )
                }
                delay(4000)
                onEvent(DealEvent.OnSetDefaultState)
            }
        }
    }

    private fun doChangeTitle(event: DealEvent.OnTitleChange) {
        _state.update {
            it.copy(title = event.value)
        }
    }

    private fun doChangeDescription(event: DealEvent.OnDescriptionChange) {
        _state.update {
            it.copy(description = event.value)
        }
    }

    private fun doChangeCategory(event: DealEvent.OnCategoryChange) {
        _state.update {
            it.copy(
                category = event.value
            )
        }
    }

    private fun doChangeIsFree(event: DealEvent.OnIsFreeChange) {
        _state.update {
            it.copy(isFree = event.value)
        }
    }

    private fun doChangePrice(event: DealEvent.OnPriceChange) {
        _state.update {
            it.copy(price = event.value)
        }
    }

    private fun doChangeOfferPrice(event: DealEvent.OnOfferPriceChange) {
        _state.update {
            it.copy(offerPrice = event.value)
        }
    }

    private fun doChangePublished(event: DealEvent.OnPublishedChange) {
        _state.update {
            it.copy(published = event.value)
        }
    }

    private fun doChangeExpirationDate(event: DealEvent.OnExpirationDateChange) {
        _state.update {
            it.copy(expirationDate = event.value)
        }
    }

    private fun doChangeProvider(event: DealEvent.OnProviderChange) {
        _state.update {
            it.copy(provider = event.value)
        }
    }

    private fun doChangeProviderUrl(event: DealEvent.OnProviderUrlChange) {
        _state.update {
            it.copy(providerUrl = event.value)
        }
    }

    private fun doChangeThumbnail(event: DealEvent.OnThumbnailChange) {
        _state.update {
            it.copy(
                thumbnail = event.value,
            )
        }
    }

    private fun doChangeImages(event: DealEvent.OnImagesChange) {
        _state.update {
            it.copy(images = event.values)
        }
    }

    private fun doChangeVideoUrl(event: DealEvent.OnVideoUrlChange) {
        _state.update {
            it.copy(
                videoUrl = event.value,
            )
        }
    }

    private fun doChangeCouponCode(event: DealEvent.OnCouponCodeChange) {
        _state.update {
            it.copy(
                couponCode = event.value,
            )
        }
    }

    private fun doChangeTags(event: DealEvent.OnTagsChange) {
        _state.update {
            it.copy(
                selectedTags = event.values,
            )
        }
    }

    private fun doChangeShippingCosts(event: DealEvent.OnShippingCostChange) {
        _state.update {
            it.copy(
                shippingCosts = event.value,
            )
        }
    }

    fun getTags(queryText: String? = null) = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            tagsUseCase.getTags(queryText).collectLatest { status ->
                _state.update {
                    when (status) {
                        is Results.Loading -> _state.value.copy(isLoading = true, error = null)
                        is Results.Success -> _state.value.copy(
                            listTag = status.data ?: listOf(),
                            error = null
                        )

                        is Results.Error -> _state.value.copy(error = getString(status.error?.message!!))
                    }

                }
            }

        } catch (e: Exception) {
            _state.update {
                it.copy(
                    error = e.message
                )
            }
            delay(4000)
            _state.value = DealsState()
        }
    }

    fun getCategories() = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            _state.update {
                it.copy(
                    categories = categoriesUseCase.getCategories().categories,
                    isLoading = false,
                    error = null
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    error = e.message
                )
            }
            delay(4000)
            _state.value = DealsState()
        }
    }

    fun getDeals() = viewModelScope.launch {
            useCase.getDeals(query = DealsQuery(limit = 40)).collectLatest { status ->
                when (status) {
                    is Results.Loading -> _state.value = _state.value.copy(isLoading = true)
                    is Results.Success -> {
                        _state.update {
                            it.copy(
                                deals = status.data?.deals
                                    ?: listOf(),
                                isLoading = false,
                                error = null,
                            )
                        }
                    }

                    is Results.Error -> {
                        status.error?.let { error ->
                            _state.update {
                                it.copy(
                                    error = getString(error.message),
                                    isLoading = false
                                )
                            }
                        }
                        delay(4000)
                        onEvent(DealEvent.OnSetDefaultState)
                    }
                }

            }
    }

    fun deleteDeal(dealModel: DealModel?) {
        try {
            viewModelScope.launch {
                if (dealModel != null) {
                    useCase.deleteDeal(dealModel) {
                    }
                }
            }
        } catch (e: Exception) {
            println("Fehler beim deleten deals  ${e.message}")
        }
    }

}