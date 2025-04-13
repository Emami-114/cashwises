package useCase

import data.model.DealModel
import data.repository.ImageUploadRepository
import domain.model.CategoryModel
import domain.model.ImageModel
import domain.repository.CategoryRepository
import domain.repository.Result
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CategoryUseCase : KoinComponent {
    private val repository: CategoryRepository by inject()
    private var imageRepository = ImageUploadRepository()
    suspend fun getCategories(): Flow<Result<List<CategoryModel>>> {
        try {
            return repository.getCategories()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCategory(id: String): Flow<Result<CategoryModel>> {
        try {
            return repository.getCategory(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun createCategory(
        categoryModel: CategoryModel, onSuccess: () -> Unit
    ) {
        try {
            repository.createCategory(categoryModel).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                } else {
                    throw Exception("Oh, something went wrong!")
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun updateCategory(categoryModel: CategoryModel, onSuccess: () -> Unit) {
        try {
            repository.updateCategory(categoryModel).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                } else {
                    throw Exception("Failed update category")
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteCategory(id: String, onSuccess: () -> Unit) {
        repository.deleteCategory(id).let { isSuccess ->
            if (isSuccess) {
                onSuccess()
            } else {
                println("Failed deleting Category")
            }
        }
    }

}