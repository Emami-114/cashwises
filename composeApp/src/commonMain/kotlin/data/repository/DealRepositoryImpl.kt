package data.repository

import data.model.DealsQuery
import data.model.MarkedDealForUser
import data.repository.ApiConfig.httpClient
import domain.model.DealModel
import domain.model.DealsModel
import domain.repository.AuthRepository
import domain.repository.DealRepository
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ui.settings


class DealRepositoryImpl : DealRepository {
    private val client = httpClient

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getDeals(
        query: DealsQuery
    ): DealsModel? {
        return try {
            val response = client.get("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                parameter("query", query.searchQuery)
                parameter("page", query.page)
                parameter("limit", query.limit)
                val filterCategories = query.categories
                val filterTags = query.filterTags
                parameter("categories", filterCategories)
                parameter("tags", filterTags)
            }
            response.body<DealsModel>()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSingleDeal(id: String): DealModel? {
        return try {
            val response = client.get("${ApiConfig.BASE_URL}/deals/$id") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.body<DealModel>()

            println("test deal Repo ${response.title}")
            response
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