package data.repository

import data.model.DealModel
import data.model.DealsQuery
import data.repository.ApiConfig.httpClient
import domain.enums.ErrorType
import domain.model.DealDetailModel
import domain.model.DealModelDto
import domain.repository.DealRepository
import domain.repository.Result
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
import io.ktor.http.isSuccess
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException


class DealRepositoryImpl : DealRepository {
    private val client = httpClient

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getDeals(
        query: DealsQuery
    ): Flow<Result<List<DealModel>>> = flow {
        emit(Result.Loading())
        try {
            val result = client.get("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                parameter("searchTerm", query.searchTerm)
                parameter("page", query.page)
                parameter("limit", query.limit)
                parameter("category", query.category)
                parameter("tag", query.tag)
            }.body<List<DealModelDto>>()
            val mappedResult = result.map { dto ->
                dto.toModel()
            }
            emit(Result.Success(mappedResult))
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Result.Error(ErrorType.NoInternetConnection))
                is HttpRequestTimeoutException -> emit(Result.Error(ErrorType.RequestTimeout))
                else -> emit(Result.Error(ErrorType.UnknownError))
            }
        }
    }

    override suspend fun getSingleDeal(id: String): Flow<Result<DealDetailModel?>> = flow {
        emit(Result.Loading())
        try {
            val result = client.get("${ApiConfig.BASE_URL}/deals/$id") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.body<DealDetailModel>()
            emit(Result.Success(result))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addDeal(dealDetailModel: DealDetailModel): Boolean {
        return try {
            client.post("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(dealDetailModel)
            }.status.isSuccess()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateDeal(
        title: String,
        description: String,
        category: String?,
        isFree: Boolean?,
        price: Double?,
        offerPrice: Double?,
        published: Boolean?,
        expirationDate: String?,
        provider: String?,
        providerUrl: String?,
        thumbnail: String?,
        images: List<String>?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDeal(id: String): Boolean {
        try {
            return client.delete("${ApiConfig.BASE_URL}/deals/$id") {
                bearerAuth(ApiConfig.userToken)
            }.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }
}