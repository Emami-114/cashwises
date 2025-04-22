package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealDetailModel(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val categories: List<String>? = null,
    val isFree: Boolean? = null,
    val price: Double? = null,
    val offerPrice: Double? = null,
    val isPublish: Boolean? = null,
    val expirationDate: String? = null,
    val provider: String? = null,
    val providerUrl: String? = null,
    val thumbnailUrl: String? = null,
    val imagesUrl: List<String>? = null,
    val userId: String? = null,
    val videoUrl: String? = null,
    // New Variable
    val couponCode: String? = null,
    val tags: List<String>? = null,
    val shippingCost: Double? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val voteCount: Int = 100
)
