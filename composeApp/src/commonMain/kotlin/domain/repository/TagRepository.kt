package domain.repository

import data.model.DealsQuery
import domain.model.DealModel
import domain.model.TagModel
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun getTags(query: String?): Flow<Results<List<TagModel>>>
    suspend fun createTag(tagModel: TagModel): Boolean
    suspend fun deleteTag(id: String): Boolean
}