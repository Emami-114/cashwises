package data.repository

import data.model.DealsQuery
import domain.enums.ErrorType
import domain.model.DealModel
import domain.model.TagModel
import domain.repository.ResultStatus
import domain.repository.Results
import domain.repository.TagRepository
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ui.settings

class TagRepositoryImpl : TagRepository {
    private val client = ApiConfig.httpClient
    override suspend fun getTags(query: String?): Flow<Results<List<TagModel>>> = flow {
        emit(Results.Loading())
        try {
            val results = client.get("${ApiConfig.BASE_URL}/tags") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                parameter("query", query)
            }.body<List<TagModel>>()
            emit(Results.Success(results))
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Results.Error(ErrorType.NoInternetConnection))
                is HttpRequestTimeoutException -> emit(Results.Error(ErrorType.RequestTimeout))
                else -> emit(Results.Error(ErrorType.UnknownError))
            }
        }
    }

    override suspend fun createTag(tagModel: TagModel): Boolean {
        return try {
            val response = client.post("${ApiConfig.BASE_URL}/tags") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(tagModel)
            }
            response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteTag(id: String): Boolean {
        try {
            val response = client.delete(urlString = "${ApiConfig.BASE_URL}/tags/$id") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }
}