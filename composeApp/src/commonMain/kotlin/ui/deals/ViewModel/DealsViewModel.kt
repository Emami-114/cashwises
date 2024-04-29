package ui.deals.ViewModel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.DealModel
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
import ui.deals.DetailDealScreen
import ui.settings
import useCase.CategoryUseCase
import useCase.DealsUseCase

class DealsViewModel : ViewModel(), KoinComponent {
    private val useCase: DealsUseCase by inject()
    private val _state = MutableStateFlow(DealsState())
    private val categoriesUseCase: CategoryUseCase by inject()
    val state = _state.asStateFlow()
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Lazily,
//            DealsState()
//        )

    init {
        getDeals()
    }

    fun doChangeDetailView(dealModel: DealModel?) {
        _state.update {
            it.copy(
                dealModel = dealModel
            )
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
            is DealEvent.OnSetDefaultState -> _state.value = DealsState()
        }
    }


    fun doChangeThumbnail(image: ImageModel?) {
        _state.update {
            it.copy(thumbnailByte = image)
        }
    }

    fun doChangeImages(image: List<ImageModel>?) {
        _state.update {
            it.copy(imagesByte = image)
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
                    println("image path von upload image $imagesPath")
                }
            }
            val deal = DealModel(
                title = _state.value.title,
                description = _state.value.description,
                category = _state.value.category,
                isFree = _state.value.isFree,
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
            it.copy(thumbnail = event.value)
        }
    }

    private fun doChangeImages(event: DealEvent.OnImagesChange) {
        _state.update {
            it.copy(images = event.values)
        }
    }

    private fun doChangeVideoUrl(event: DealEvent.OnVideoUrlChange) {
        _state.update {
            it.copy(videoUrl = event.values)
        }
    }

    fun getDeals() = viewModelScope.launch {
        try {
            _state.value = _state.value.copy(isLoading = true)
            _state.update {
                it.copy(
                    deals = useCase.getDeals()?.deals
                        ?: listOf(),
                    isLoading = false,
                    error = null,
                )
            }
            _state.update {
                it.copy(categories = categoriesUseCase.getCategories().categories)
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message
                )
            }
            delay(4000)
            onEvent(DealEvent.OnSetDefaultState)
        }
    }

}