package data.repository

import data.model.DealsQuery
import data.repository.ApiConfig.httpClient
import domain.enums.ErrorType
import domain.model.DealModel
import domain.model.DealsModel
import domain.repository.DealRepository
import domain.repository.Results
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException


class DealRepositoryImpl : DealRepository {
    private val client = httpClient

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getDeals(
        query: DealsQuery
    ): Flow<Results<DealsModel>> = flow {
        emit(Results.Loading())
        try {
            val result = client.get("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                parameter("query", query.searchQuery)
                parameter("page", query.page)
                parameter("limit", query.limit)
                val filterCategories = query.categories
                val filterTags = query.filterTags
                parameter("categories", filterCategories)
                parameter("tags", filterTags)
            }.body<DealsModel>()
            emit(Results.Success(result))
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Results.Error(ErrorType.NoInternetConnection))
                is HttpRequestTimeoutException -> emit(Results.Error(ErrorType.RequestTimeout))
                else -> emit(Results.Error(ErrorType.UnknownError))
            }
        }
    }

    override suspend fun getSingleDeal(id: String): Flow<Results<DealModel?>> = flow {
        emit(Results.Loading())
        try {
            val result = client.get("${ApiConfig.BASE_URL}/deals/$id") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.body<DealModel>()
            emit(Results.Success(result))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addDeal(dealModel: DealModel): Boolean {
        return try {
            client.post("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(dealModel)
            }.status.value in 200..300
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