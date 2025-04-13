package domain.repository

import domain.model.DealVoteModel
import kotlinx.coroutines.flow.Flow

interface DealVoteRepository {
    suspend fun addDealVote(dealVoteModel: DealVoteModel): Boolean
    suspend fun getDealVotesByDealId(dealId: String): Flow<Result<List<DealVoteModel>>>
    suspend fun getDealVotesByDealIdAndUserId(userId: String,dealId: String): Flow<Result<DealVoteModel?>>
    suspend fun deleteDealVoteByDealIdAndUserId(dealId: String,userId: String): Boolean
}