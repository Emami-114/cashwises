package data.repository

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bad_request_error
import cashwises.composeapp.generated.resources.conflict_error
import cashwises.composeapp.generated.resources.not_found_error
import cashwises.composeapp.generated.resources.unauthorized_error
import domain.enums.HttpError
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString

object ApiConfig {
    const val BASE_URL = "https://cashwises.backend.api.cwcash.de/api"
//    const val BASE_URL = "http://192.168.178.22:8000/api"

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }, contentType = ContentType.Any)
        }
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                if (statusCode != 0 && statusCode !in 200..300) {
                    when (statusCode) {
                        400 -> {
                            throw Exception(HttpError.BadRequest(getString(Res.string.bad_request_error)).value)
                        }

                        401 -> {
                            throw Exception(HttpError.Unauthorized(getString(Res.string.unauthorized_error)).value)
                        }

                        403 -> {

                        }

                        404 -> {
                            throw Exception(HttpError.NotFound(getString(Res.string.not_found_error)).value)
                        }

                        409 -> {
                            throw Exception(HttpError.Conflict(getString(Res.string.conflict_error)).value)
                        }
                    }
                }

            }
        }
    }
}