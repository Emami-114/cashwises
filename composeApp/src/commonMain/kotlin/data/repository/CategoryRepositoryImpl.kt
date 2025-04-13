package data.repository

import data.model.DealModel
import domain.enums.ErrorType
import domain.model.CategoryModel
import domain.repository.CategoryRepository
import domain.repository.Result
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException

class CategoryRepositoryImpl : CategoryRepository {
    private val client = ApiConfig.httpClient
    private val baseUrl = ApiConfig.BASE_URL

    override suspend fun createCategory(categoryModel: CategoryModel): Boolean {
        try {
            val response = client.post("${baseUrl}/categories") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(categoryModel)
            }
            return response.status.isSuccess()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategories(): Flow<Result<List<CategoryModel>>> = flow {
        emit(Result.Loading())
        try {
            val response = client.get("${baseUrl}/categories") {
                bearerAuth(ApiConfig.userToken)
            }
            emit(Result.Success(response.body<List<CategoryModel>>()))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCategory(id: String): Flow<Result<CategoryModel>> = flow {
        emit(Result.Loading())
        try {
            val response = client.get("${baseUrl}/categories/$id") {
                bearerAuth(ApiConfig.userToken)
            }.body<CategoryModel>()
            emit(Result.Success(response))
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Result.Error(ErrorType.NoInternetConnection))
                is HttpRequestTimeoutException -> emit(Result.Error(ErrorType.RequestTimeout))
                else -> emit(Result.Error(ErrorType.UnknownError))
            }
        }
    }

    override suspend fun updateCategory(categoryModel: CategoryModel): Boolean {
        try {
            val response = client.patch("${baseUrl}/categories/${categoryModel.id}") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(categoryModel)
            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteCategory(id: String): Boolean {
        try {
            val response = client.delete("${baseUrl}/categories/$id") {
                bearerAuth(ApiConfig.userToken)
            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }
}