package data.repository

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import data.model.LoginModel
import data.model.RegisterModel
import domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
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
            println("${response.status}")
            response.status.value in 200..300
        } catch (e: Exception) {
            println("Register Failed ${e.message}")
            false
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        val body = LoginModel(email, password)
        val respose = client.post("${baseUrl}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        return if (respose.status.value in 200..300) {
            println(
                "token from login ${
                    respose.body<TokenModel>().token
                }"
            )
            settings.putString("TOKEN2", respose.body<TokenModel>().token)
            true
        } else {
            false
        }

    }

    override suspend fun coockie() {
        val client = ApiConfig.httpClient
        client.get("${baseUrl}/api/auth/me") // Die URL sollte die URL des Servers sein, von dem Sie Cookies abrufen möchten

        val cookies = client.cookies("${baseUrl}/api/auth/me")

        for (cookie in cookies) {
            println("Cookie: ${cookie.name}=${cookie.value}/api/auth/me")
        }
//    client.close()
    }

    override suspend fun passwordForget(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        client.post("${baseUrl}/api/auth/logout") {

        }
    }
}
