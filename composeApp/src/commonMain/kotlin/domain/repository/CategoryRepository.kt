package domain.repository

import domain.model.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun createCategory(categoryModel: CategoryModel): Boolean

    suspend fun getCategories(): Flow<Result<List<CategoryModel>>>

    suspend fun getCategory(id: String): Flow<Result<CategoryModel>>

    suspend fun updateCategory(categoryModel: CategoryModel): Boolean
    suspend fun deleteCategory(id: String): Boolean
}