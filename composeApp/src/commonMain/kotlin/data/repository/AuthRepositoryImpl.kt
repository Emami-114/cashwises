package data.repository

import data.model.LoginModel
import data.model.RegisterModel
import domain.repository.AuthRepository
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepositoryImpl : AuthRepository {
    private val client = ApiConfig.httpClient
    private val baseUrl = ApiConfig.BASE_URL
    override suspend fun register(registerModel: RegisterModel): Boolean {
        try {
            val response = client.post("${baseUrl}/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(registerModel)
            }
            println("${response.status}")
            return response.status.value in 200..300
        } catch (e: Exception) {
            println("Register Failed ${e.message}")
            return false
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        val body = LoginModel(email, password)
        val respose = client.post("${baseUrl}/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }
        println("Test: response ${respose.status}")
        return respose.status.value in 200..300
    }

    override suspend fun passwordForget(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        client.post("${baseUrl}/api/auth/logout") {

        }
    }
}