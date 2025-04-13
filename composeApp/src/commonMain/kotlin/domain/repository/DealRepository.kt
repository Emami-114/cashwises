package domain.repository

import data.model.DealModel
import data.model.DealsQuery
import domain.model.DealDetailModel
import kotlinx.coroutines.flow.Flow

interface DealRepository {
    suspend fun getDeals(
        query: DealsQuery
    ): Flow<Result<List<DealModel>>>

    suspend fun getSingleDeal(id: String): Flow<Result<DealDetailModel?>>
    suspend fun addDeal(dealModel: DealDetailModel): Boolean
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