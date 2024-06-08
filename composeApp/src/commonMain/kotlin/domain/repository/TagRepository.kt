package domain.repository

import data.model.DealsQuery
import domain.model.TagModel

interface TagRepository {
    suspend fun getTags(query: String?): List<TagModel>
    suspend fun createTag(tagModel: TagModel): Boolean
    suspend fun deleteTag(id: String): Boolean
}