package domain.repository

import domain.model.CategoriesModel
import domain.model.CategoryModel

interface CategoryRepository {
    suspend fun createCategory(categoryModel: CategoryModel): Boolean

    suspend fun getCategories(): CategoriesModel

    suspend fun getCategory(id: String): CategoryModel

    suspend fun updateCategory(categoryModel: CategoryModel): Boolean
    suspend fun deleteCategory(id: String): Boolean
}