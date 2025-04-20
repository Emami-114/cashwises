package data.repository

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.conflict_error
import cashwises.composeapp.generated.resources.not_found_error
import cashwises.composeapp.generated.resources.unauthorized_error
import domain.enums.ErrorType
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.jetbrains.compose.resources.getString
import ui.settings

object ApiConfig {
    const val BASE_URL = "https://api2.maldeals.de/api"
    const val IMAGE_URL = "https://minio.maldeals.de"

    //    const val BASE_URL = "http://192.168.178.22:8000/api"
    var userToken = settings.getString("TOKEN", "Token not found")
    private var API_KEY = settings.getString("api_key", "")


    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, LocalDateTimeIso8601Serializer)
                }
            }, contentType = ContentType.Any)

        }
        defaultRequest {
             header("X-API-Key", API_KEY)
        }


        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                if (statusCode != 0 && statusCode !in 200..300) {
                    when (statusCode) {
                        400 -> {
                            throw Exception(getString(ErrorType.BadRequest.message))
                        }

                        401 -> {
                            throw Exception(getString(Res.string.unauthorized_error))
                        }

                        403 -> {

                        }

                        404 -> {
                            throw Exception(getString(Res.string.not_found_error))
                        }

                        409 -> {
                            throw Exception(getString(Res.string.conflict_error))
                        }
                    }
                }

            }
        }
    }
}