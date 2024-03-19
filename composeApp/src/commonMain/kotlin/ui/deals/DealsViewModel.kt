package ui.deals

import androidx.compose.runtime.mutableStateOf
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.readByteArray
import data.repository.ApiConfig
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.ImageModel
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.InternalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import useCase.DealsUseCase

class DealsViewModel : ViewModel(), KoinComponent {
    private val useCase: DealsUseCase by inject()
    private val _state = MutableStateFlow(DealsState())
    val state = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            DealsState()
        )

    init {
        getDeals()
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


    fun doChangeImage(image: ImageModel) {
        _state.update {
            it.copy(thumbnailByte = image)
        }
    }

    fun doChangeImages(image: ImageModel) {
        _state.value.imagesByte?.plus(image)
    }

    private fun doCreateDeal(event: DealEvent.OnAction) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            useCase.uploadImage(
                _state.value.thumbnailByte?.byteArray ?: ByteArray(0),
                fileName = _state.value.thumbnailByte?.name ?: "",
                path = { imagePath ->
                    _state.update { it.copy(thumbnail = _state.value.thumbnailByte?.name) }
                }
            )
            if (_state.value.images != null) {
                _state.value.imagesByte?.forEach { imageByte ->
                    useCase.uploadImage(
                        imageByte.byteArray,
                        fileName = imageByte.name,
                        path = { path ->
                            _state.update { it.copy(images = it.images?.plus(path)) }
                        })
                }
            }
            useCase.addDeals(
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
                videoUrl = _state.value.videoUrl
            )
            onEvent(event = DealEvent.OnSetDefaultState)
            _state.value = _state.value.copy(isLoading = false)
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
            it.copy(category = event.value)
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

    private fun getDeals() = viewModelScope.launch {
        println("list: ${useCase.getDeals().deals}")
        _state.value.copy(isLoading = true)
        _state.update {
            it.copy(
                deals = useCase.getDeals().deals,
                isLoading = false,
                error = null,
            )
        }
    }

}