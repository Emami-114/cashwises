package domain.model

import data.model.DealModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class DealModelDto(
    val id: String,
    val title: String,
    val isFree: Boolean? = null,
    val price: Double? = null,
    val offerPrice: Double? = null,
    val expirationDate: String? = null,
    val provider: String? = null,
    val providerUrl: String? = null,
    val thumbnailUrl: String? = null,
    val userId: String? = null,
    val couponCode: String? = null,
    val updatedAt: String? = null
) {
    fun toModel(): DealModel {
        return DealModel(
            id = this.id,
            title = this.title,
            isFree = this.isFree,
            price = this.price,
            offerPrice = this.offerPrice,
            expirationDate = this.expirationDate,
            provider = this.provider,
            providerUrl = this.providerUrl,
            thumbnailUrl = this.thumbnailUrl,
            userId = this.userId,
            couponCode = this.couponCode,
            updatedAt = this.updatedAt
        )
    }
}