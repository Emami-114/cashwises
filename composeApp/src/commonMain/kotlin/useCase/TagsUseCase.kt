package useCase

import domain.model.TagModel
import domain.repository.Result
import domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TagsUseCase : KoinComponent {
    private val repository: TagRepository by inject()

    suspend fun getTags(query: String?): Flow<Result<List<TagModel>>> {
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