package data.repository

import data.repository.ApiConfig.httpClient
import domain.model.DealModel
import domain.model.DealsModel
import domain.repository.DealRepository
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
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
        query: String,
        page: Int,
        limit: Int,
        token: String
    ): DealsModel? {
        return try {
            val response = client.get("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(settings.getString("TOKEN2", "Token not found"))
            }
            println("Token von get deals ${settings.getString("TOKEN2", "Token not found")}")
            response.body<DealsModel>()
        } catch (e: Exception) {
            println("Failed repository get deals: ${e.message}")
            null
            //            throw e
        }
    }

    override suspend fun addDeal(dealModel: DealModel) {
        try {
            client.post("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
                bearerAuth(settings.getString("TOKEN2", "Token not found"))

                setBody(dealModel)
            }
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

    override suspend fun deleteDeal(id: String) {
        client.delete("${ApiConfig.BASE_URL}/deals/$id") {
            bearerAuth(settings.getString("TOKEN2", "Token not found"))
        }

    }
}