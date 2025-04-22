package data.model

import domain.model.DealVoteModel
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.until
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

@Serializable
data class DealModel(
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
    val voteCount: Int = 0,
    val couponCode: String? = null,
    var dealVote: List<DealVoteModel> = listOf(),
    val updatedAt: String? = null
) {
    val currentCreatedHour = Clock.System.now().until(
        Instant.parse(updatedAt ?: ""), unit = DateTimeUnit.HOUR
    ).absoluteValue
    val currentCreatedMinute = Clock.System.now().until(
        Instant.parse(updatedAt ?: ""), unit = DateTimeUnit.MINUTE
    ).absoluteValue
    val currentCreatedDay = Clock.System.now().daysUntil(
        Instant.parse(updatedAt ?: ""), timeZone = TimeZone.UTC
    ).absoluteValue

    fun expirationDateToDay(): Int {
        return if (!expirationDate.isNullOrEmpty())
            Clock.System.now()
                .daysUntil(Instant.parse(expirationDate), timeZone = TimeZone.UTC)
        else
            0
    }

    fun hasExpirationDate(): Boolean {
        return expirationDate != null
    }
}