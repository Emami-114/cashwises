package data.repository

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bad_request_error
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
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import ui.settings

object ApiConfig {
    const val BASE_URL = "https://cashwises.backend.api.cwcash.de/api"

//    const val BASE_URL = "http://192.168.178.22:8000/api"
    var userToken = settings.getString("TOKEN", "Token not found")
    private const val API_KEY = "4FeR43JKi453NO0mv4HN657aGD34Vc%2"

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
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