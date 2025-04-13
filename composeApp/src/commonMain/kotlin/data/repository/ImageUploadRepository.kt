package data.repository

import domain.model.ImageModel
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import io.ktor.utils.io.InternalAPI
import utils.resizeImage

class ImageUploadRepository {
    @OptIn(InternalAPI::class, InternalAPI::class)
    suspend fun uploadThumbnail(
        imageModel: ImageModel,
        bucketName: String = "",
        imagePath: (String) -> Unit
    ) {
        try {
            if (imageModel.byteArray.isEmpty()) {
                println("❗️Image byte array is empty.")
                return
            }
            val response = ApiConfig.httpClient.submitFormWithBinaryData(
                url = "${ApiConfig.BASE_URL}/images/$bucketName",
                formData = formData {
                    append(
                        key = "file",
                        value = resizeImage(imageModel.byteArray, 300, 300, quality = 50),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "form-data; name=\"file\"; filename=\"${imageModel.name}\""
                            )
                        }
                    )
                }
            ) {
                method = HttpMethod.Post
                bearerAuth(ApiConfig.userToken)
            }
            if (response.status.isSuccess()) {
                imagePath(response.bodyAsText())
                println("Image Path: ${response.bodyAsText()} ")
            }
        } catch (e: Exception) {
            println("Failed image upload ${e.message}")
            throw e
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun uploadImages(
        imagesModel: List<ImageModel>,
        bucketName: String = "",
        imagePaths: (List<String>) -> Unit
    ) {
        try {
            if (imagesModel.isEmpty()) {
                println("❗️Image byte array is empty.")
                return
            }
            val response = ApiConfig.httpClient.submitFormWithBinaryData(
                url = "${ApiConfig.BASE_URL}/images/multiple/$bucketName",
                formData = formData {
                    imagesModel.forEach { imageModel ->
                        append(
                            key = "files",
                            value = resizeImage(imageModel.byteArray, 800, 800, quality = 50),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, "image/jpeg")
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "form-data; name=\"files\"; filename=\"${imageModel.name}\""
                                )
                            }
                        )
                    }
                }
            ) {
                method = HttpMethod.Post
                bearerAuth(ApiConfig.userToken)
            }
            if (response.status.isSuccess()) {
                imagePaths(response.body<List<String>>())
                println("Image Paths: ${response.body<List<String>>()} ")
            }
        } catch (e: Exception) {
            println("Failed image upload ${e.message}")
            throw e
        }
    }

    suspend fun deleteImage(path: String): Boolean {
        try {
            return ApiConfig.httpClient.delete("${ApiConfig.BASE_URL}/images/$path") {
                bearerAuth(ApiConfig.userToken)
            }.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }
}