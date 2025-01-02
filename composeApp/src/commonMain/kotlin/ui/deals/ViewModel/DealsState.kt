package ui.deals.ViewModel

import domain.model.CategoryModel
import domain.model.DealModel
import domain.model.ImageModel
import domain.model.SmallDealModel
import domain.model.TagModel
import kotlinx.serialization.SerialName
import ui.settings

data class DealsState(
    val id: String = "",
    val deals: List<SmallDealModel> = listOf(),
    val categories: List<CategoryModel> = listOf(),
    val isLoading: Boolean = false,
    val isItemExpanded: Boolean = settings.getBoolean("is_item_expanded", false),
    val error: String? = null,
    val title: String = "",
    val description: String = "",
    var category: List<String>? = null,
    val isFree: Boolean = false,
    val price: String? = null,
    val offerPrice: String? = null,
    val published: Boolean = true,
    val expirationDate: String? = null,
    val provider: String? = null,
    val providerUrl: String? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null,
    val userId: String? = null,
    val videoUrl: String? = null,
    val thumbnailByte: ImageModel? = null,
    var imagesByte: List<ImageModel>? = null,
    val dealCreatedSuccess: Boolean = false,
    val selectedDeal: DealModel? = null,
    val couponCode: String? = null,
    val selectedTags: List<String> = listOf(),
    val shippingCosts: Double? = null,
    val listTag: List<TagModel> = listOf()
)
