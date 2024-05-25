package data.repository

import domain.model.UserModel
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ui.settings

class UserRepository {
    private val toke = settings.getString("TOKEN", "Token not found")
    var user: UserModel? = null
    var userIsLogged: Boolean = false

    companion object {
        var INSTANCE: UserRepository = UserRepository()
    }

    suspend fun getMe() {
        try {
            if (toke.isEmpty().not()) {
                val me = ApiConfig.httpClient.get("${ApiConfig.BASE_URL}/users/me") {
                    bearerAuth(toke)
                }.body<UserModel?>()
                userIsLogged = me != null
                user = me
            }
        } catch (e: Exception) {
            println("Error GET ME Failed ${e.message}")
        }
    }

    suspend fun logout(): Boolean {
        try {
            val response = ApiConfig.httpClient.post("${ApiConfig.BASE_URL}/auth/logout") {
                contentType(ContentType.Application.Json)
                bearerAuth(toke)
            }.status.value in 200..300
            if (response) {
                user = null
                userIsLogged = false
                settings.putString("TOKEN", "")
                return true
            }
        } catch (e: Exception) {
            throw e
        }
        return false
    }
}

