package useCase

import data.repository.ImageUploadRepository
import domain.model.CategoriesModel
import domain.model.CategoryModel
import domain.model.ImageModel
import domain.repository.CategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CategoryUseCase : KoinComponent {
    private val repository: CategoryRepository by inject()
    private var imageRepository = ImageUploadRepository()
    suspend fun getCategories(): CategoriesModel {
        try {
            return repository.getCategories()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadImage(
        imageModel: ImageModel,
        imagePaths: (String) -> Unit,
    ) {
        try {
            imageRepository.uploadImage(
                imageModel,
                subDir = "categories_images",
                imagePath = { path ->
                    if (path.isNotEmpty()) {
                        imagePaths(path)
                    } else {
                        throw Exception("oh, something went wrong!")
                    }
                })
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