package data.repository

import domain.model.CategoriesModel
import domain.model.CategoryModel
import domain.repository.CategoryRepository
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CategoryRepositoryImpl : CategoryRepository {
    private val client = ApiConfig.httpClient
    private val baseUrl = ApiConfig.BASE_URL
    override suspend fun createCategory(categoryModel: CategoryModel): Boolean {
        try {
            val response = client.post("${baseUrl}/category") {
                contentType(ContentType.Application.Json)
                setBody(categoryModel)
            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            println("Failed create Category")
            throw e
        }
    }

    override suspend fun getCategories(): CategoriesModel {
        try {
            return client.get("${baseUrl}/categories").body()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategory(id: String): CategoryModel {
        try {
            return client.get("${baseUrl}/category/$id")
                .body<CategoryModel>()
        } catch (e: Exception) {
            println("Failed get category with id ${e.message}")
            throw e
        }
    }

    override suspend fun updateCategory(categoryModel: CategoryModel): Boolean {
        try {
            val response = client.patch("${baseUrl}/category/${categoryModel.id}") {
                contentType(ContentType.Application.Json)
                setBody(categoryModel)
            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            println("Failed update Category")
            throw e
        }
    }

    override suspend fun deleteCategory(id: String): Boolean {
        try {
            val response = client.delete("${baseUrl}/category/$id")
            return response.status.value in 200..300
        } catch (e: Exception) {
            println("Failed deleting category")
            throw e
        }
    }
}