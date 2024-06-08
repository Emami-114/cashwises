package domain.repository

import data.model.DealsQuery
import data.model.MarkedDealForUser
import domain.model.DealModel
import domain.model.DealsModel
import kotlinx.serialization.SerialName

interface DealRepository {
    suspend fun getDeals(
        query: DealsQuery
    ): DealsModel?

    suspend fun getSingleDeal(id: String): DealModel?
    suspend fun addDeal(dealModel: DealModel): Boolean
    suspend fun updateDeal(
        title: String,
        description: String,
        category: String? = null,
        isFree: Boolean? = null,
        price: Double? = null,
        offerPrice: Double? = null,
        published: Boolean? = null,
        expirationDate: String? = null,
        provider: String? = null,
        providerUrl: String? = null,
        thumbnail: String? = null,
        images: List<String>? = null,
    )

    suspend fun deleteDeal(id: String): Boolean
}