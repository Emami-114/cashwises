package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DealsModel(
    val deals: List<DealModel>
)

@Serializable
data class DealModel(
    val id: String? = null,
    val title: String,
    val description: String,
    val category: List<String>? = null,
    @SerialName("is_free")
    val isFree: Boolean? = null,
    val price: Double? = null,
    @SerialName("offer_price")
    val offerPrice: Double? = null,
    val published: Boolean? = null,
    @SerialName("expiration_date")
    val expirationDate: String? = null,
    val provider: String? = null,
    @SerialName("provider_url")
    val providerUrl: String? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("video_url")
    val videoUrl: String? = null,
    // New Variable
    @SerialName("coupon_code")
    val couponCode: String? = null,
    val tags: List<String>? = null,
    @SerialName("shipping_costs")
    val shippingCosts: Double? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)