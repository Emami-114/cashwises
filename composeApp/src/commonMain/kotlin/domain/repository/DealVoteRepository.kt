package domain.repository

import domain.model.DealVoteModel
import kotlinx.coroutines.flow.Flow

interface DealVoteRepository {
    suspend fun addDealVote(dealVoteModel: DealVoteModel): Boolean
    suspend fun gerDealVotesByUserId(userId: String): List<DealVoteModel>
    suspend fun getDealVotesByDealId(dealId: String): List<DealVoteModel>
    suspend fun getDealVotesByDealIdAndUserId(userId: String,dealId: String): DealVoteModel?
    suspend fun deleteDealVoteByDealIdAndUserId(dealId: String,userId: String): Boolean
}