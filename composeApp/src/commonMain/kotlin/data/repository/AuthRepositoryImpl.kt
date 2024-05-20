package data.repository

import data.model.LoginModel
import data.model.RegisterModel
import data.model.VerificationModel
import domain.repository.AuthRepository
import io.ktor.client.call.body
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.Serializable
import ui.settings

@Serializable
data class TokenModel(val token: String)
class AuthRepositoryImpl : AuthRepository {
    private val client = ApiConfig.httpClient
    private val baseUrl = ApiConfig.BASE_URL
    override suspend fun register(registerModel: RegisterModel): Boolean {
        return try {
            val response = client.post("${baseUrl}/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(registerModel)
            }
            response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        val body = LoginModel(email, password)
        return try {
            val respose = client.post("${baseUrl}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
            if (respose.status.value in 200..300) {
                settings.putString("TOKEN", respose.body<TokenModel>().token)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun passwordForget(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun verification(email: String, code: String): Boolean {
        val body = VerificationModel(email, code)
        try {
            val response = client.post("$baseUrl/auth/verification") {
                contentType(ContentType.Application.Json)
                setBody(body)

            }
            return response.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun logout() {
        client.post("${baseUrl}/api/auth/logout") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.getString("TOKEN", "Token not found"))
        }
    }
}
