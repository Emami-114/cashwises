package domain.repository

import domain.model.TagModel

interface TagRepository {
    suspend fun getTags(): List<TagModel>
    suspend fun createTag(tagModel: TagModel): Boolean
    suspend fun deleteTag(id: String)
}