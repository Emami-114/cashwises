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

    override suspend fun getDeals(): DealsModel {
        try {
            val response = client.get("${ApiConfig.BASE_URL}/deals")
            return response.body<DealsModel>()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addDeal(
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
        images: List<String>?,
        userId: String?,
        videoUrl: String?,
    ) {
        try {
            val body = DealModel(
                title = title,
                description = description,
                category = category,
                isFree = isFree,
                price = price,
                offerPrice = offerPrice,
                published = published,
                expirationDate = expirationDate,
                provider = provider,
                providerUrl = providerUrl,
                thumbnail = thumbnail,
                images = images,
                userId = userId,
                videoUrl = videoUrl,
            )
            client.post("${ApiConfig.BASE_URL}/deals/") {
                contentType(ContentType.Application.Json)
                setBody(body)
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

    override suspend fun uploadImage(
        byteArray: ByteArray,
        fileName: String,
        path: (String) -> Unit
    ): Boolean {
        @OptIn(InternalAPI::class)
        try {
            val response = client.submitForm {
                url("${ApiConfig.BASE_URL}/image/")
                method = HttpMethod.Post
                body = MultiPartFormDataContent(
                    formData {
                        append("image",
                            byteArray,
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=${fileName}")
                            }
                        )
                    }
                )
            }
            path(response.body())
            return response.status.value in (200..299)
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteDeal(id: String) {
        client.delete("${ApiConfig.BASE_URL}/deals/$id")
    }
}