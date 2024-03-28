package ui.deals.ViewModel

import domain.model.DealModel
import domain.model.ImageModel
import kotlinx.serialization.SerialName

data class DealsState(
    val id: String = "",
    val deals: List<DealModel> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val title: String = "",
    val description: String = "",
    val category: String? = null,
    val isFree: Boolean? = null,
    val price: String? = null,
    val offerPrice: String? = null,
    val published: Boolean? = null,
    val expirationDate: String? = null,
    val provider: String? = null,
    val providerUrl: String? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null,
    val userId: String? = null,
    val videoUrl: String? = null,

    val thumbnailByte: ImageModel? = null,
    var imagesByte: List<ImageModel>? = null
)
