package domain.repository

import domain.model.TagModel
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun getTags(query: String?): Flow<Result<List<TagModel>>>
    suspend fun createTag(tagModel: TagModel): Boolean
    suspend fun deleteTag(id: String): Boolean
}