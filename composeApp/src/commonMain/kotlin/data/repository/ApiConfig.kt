package data.repository

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiConfig {
    const val BASE_URL = "https://cashwises.backend.api.cwcash.de/api"
    const val BASE_URL2 = "http://192.168.178.22:8000/api"

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }, contentType = ContentType.Any)
        }
    }
}