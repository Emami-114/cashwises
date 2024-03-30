package data.repository

import domain.model.ImageModel
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.util.InternalAPI

class ImageUploadRepository {
    suspend fun uploadImage(
        imageModel: ImageModel,
        subDir: String = "",
        imagePath: (List<String>) -> Unit
    ) {
        @OptIn(InternalAPI::class)
        try {
            val response = ApiConfig.httpClient.submitForm {
                url("${ApiConfig.BASE_URL}/images")
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
                imagePath(response.body<List<String>>())
            }
        } catch (e: Exception) {
            println("Failed image upload ${e.message}")
            throw e
        }
    }
}