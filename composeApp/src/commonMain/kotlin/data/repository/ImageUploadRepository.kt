package data.repository

import domain.model.ImageModel
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json

class ImageUploadRepository {

    @OptIn(InternalAPI::class)
    suspend fun uploadImage(
        imageModel: ImageModel,
        subDir: String = "",
        imagePath: (String) -> Unit
    ) {
        try {
            val response = ApiConfig.httpClient.submitForm {
                url("${ApiConfig.BASE_URL}/images/image")
                parameter("dir", subDir)
                method = HttpMethod.Post
                body = MultiPartFormDataContent(
                    formData {
                        append("image",
                            imageModel.byteArray,
                            Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=${imageModel.name}"
                                )
                            }
                        )
                    }
                )
            }
            if (response.status.value in (200..299)) {
                imagePath(Json.decodeFromString(response.bodyAsText()))
            }
        } catch (e: Exception) {
            println("Failed image upload ${e.message}")
            throw e
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun uploadImages(
        imagesModel: List<ImageModel>,
        subDir: String = "",
        imagePaths: (List<String>) -> Unit
    ) {
        try {
            val response = ApiConfig.httpClient.submitForm {
                url("${ApiConfig.BASE_URL}/images")
                parameter("dir", subDir)
                method = HttpMethod.Post
                body = MultiPartFormDataContent(
                    formData {
                        imagesModel.forEach { imageModel ->
                            append("image",
                                imageModel.byteArray,
                                Headers.build {
                                    append(HttpHeaders.ContentType, "image/png")
                                    append(
                                        HttpHeaders.ContentDisposition,
                                        "filename=${imageModel.name}"
                                    )
                                }
                            )
                        }
                    }
                )
            }
            if (response.status.value in (200..299)) {
                imagePaths(response.body<List<String>>())
            }
        } catch (e: Exception) {
            println("Failed image upload ${e.message}")
            throw e
        }
    }
}