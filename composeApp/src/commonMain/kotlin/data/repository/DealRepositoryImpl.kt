package data.repository

import domain.model.DealModel
import domain.model.DealsModel
import domain.repository.DealRepository
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import okio.Path.Companion.toPath

class DealRepositoryImpl : DealRepository {
    private val client = ApiConfig.httpClient

    override suspend fun getDeals(): DealsModel? {
        return try {
            val response = client.get("${ApiConfig.BASE_URL}/deals") {
                contentType(ContentType.Application.Json)
            }
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
        client.delete("${ApiConfig.BASE_URL}/deals/$id")
    }
}