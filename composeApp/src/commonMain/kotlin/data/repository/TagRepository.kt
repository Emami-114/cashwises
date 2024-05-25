package data.repository

import data.model.DealsQuery
import domain.model.TagModel
import domain.repository.TagRepository
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ui.settings

class TagRepositoryImpl : TagRepository {
    private val client = ApiConfig.httpClient
    override suspend fun getTags(query: String?): List<TagModel> {
        return try {
            client.get("${ApiConfig.BASE_URL}/tags") {
                contentType(ContentType.Application.Json)
                bearerAuth(settings.getString("TOKEN", "Token not found"))
                parameter("query", query)
            }.body<List<TagModel>>()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createTag(tagModel: TagModel): Boolean {
        return try {
            val response = client.post("${ApiConfig.BASE_URL}/tags") {
                contentType(ContentType.Application.Json)
                bearerAuth(settings.getString("TOKEN", "Token not found"))
                setBody(tagModel)
            }
            response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteTag(id: String): Boolean {
        val response = client.delete(urlString = "${ApiConfig.BASE_URL}/tags/$id")
        return response.status.value in 200..300
    }
}