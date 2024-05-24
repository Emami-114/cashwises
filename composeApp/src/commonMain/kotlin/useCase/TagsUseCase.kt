package useCase

import data.model.DealsQuery
import domain.model.TagModel
import domain.repository.TagRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TagsUseCase : KoinComponent {
    private val repository: TagRepository by inject()

    suspend fun getTags(query: String?): List<TagModel> {
        try {
            return repository.getTags(query = query)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun createTag(tagModel: TagModel, onSuccess: () -> Unit) {
        try {
            repository.createTag(tagModel = tagModel).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()

                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteTag(id: String, onSuccess: () -> Unit) {
        try {
            repository.deleteTag(id).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

}