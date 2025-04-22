package data.repository

import androidx.compose.runtime.mutableStateOf
import data.model.MarkedDealForUser
import domain.model.DealVoteModel
import domain.model.UserModel
import domain.model.UserRole
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import ui.settings

class UserRepository {
    var user: UserModel? = null
    var userIsLogged: Boolean = false
    var userMarkedDeals = mutableStateOf(listOf<String>())
    var userDealsVote = mutableStateOf(listOf<DealVoteModel>())
    var dealVoteRepository = DealVoteRepositoryImpl()

    companion object {
        var INSTANCE: UserRepository = UserRepository()
    }

    suspend fun getMe() {
        try {
            if (ApiConfig.userToken.isEmpty().not()) {
                val me = ApiConfig.httpClient.get("${ApiConfig.BASE_URL}/users/me") {
                    bearerAuth(ApiConfig.userToken)
                }.body<UserModel?>()
                if (me != null) {
                    userIsLogged = true
                    user = me
                    getMarkDealsForUser()
                    getUserDealsVote()
                }
            }
        } catch (e: Exception) {
            println("Error GET ME Failed ${e.message}")
        }
    }

     fun hasDealVoted(dealId: String): Boolean {
        return userDealsVote.value.any { it.dealId == dealId }
    }

    suspend fun getUserDealsVote() {
        try {
            user?.userId?.let { userId ->
                userDealsVote.value = dealVoteRepository.gerDealVotesByUserId(userId)
                println("user deals vote: ${userDealsVote.value}")
            }
        } catch (e: Exception) {
            println("Error GET User Deal Vote Failed ${e.message}")

        }
    }

    fun isDealMarked(dealId: String): Boolean {
        return userMarkedDeals.value.contains(dealId)
    }

    fun userIsAdmin(): Boolean = user?.role == UserRole.ADMIN
    fun userIsCreator(): Boolean = user?.role == UserRole.CREATOR

    suspend fun addMarkDealForUser(dealId: String): Boolean {
        try {
            val userId = user?.userId ?: ""
            val body = MarkedDealForUser(userId, dealId)
            if (userMarkedDeals.value.contains(dealId)) {
                deleteMarkDeal(markDeal = body).let { isSuccess ->
                    if (isSuccess) {
                        getMarkDealsForUser()
                        return true
                    } else {
                        return false
                    }
                }
            } else {
                val response = ApiConfig.httpClient.post("${ApiConfig.BASE_URL}/marked-deals") {
                    contentType(ContentType.Application.Json)
                    bearerAuth(ApiConfig.userToken)
                    setBody(body)
                }.status.isSuccess()
                println("marked delas: ${response}")
                response.let { isSuccess ->
                    if (isSuccess) {
                        getMarkDealsForUser()
                        return true
                    } else {
                        return false
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun getMarkDealsForUser() {
        val response =
            ApiConfig.httpClient.get("${ApiConfig.BASE_URL}/marked-deals/user/${user?.userId}") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.body<List<MarkedDealForUser>>()
        userMarkedDeals.value = response.map { it.dealId }
    }

    private suspend fun deleteMarkDeal(markDeal: MarkedDealForUser): Boolean {
        return try {
            ApiConfig.httpClient.delete("${ApiConfig.BASE_URL}/marked-deals/user/${markDeal.userId}/deal/${markDeal.dealId}") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun logout(): Boolean {
        try {
            val response = ApiConfig.httpClient.post("${ApiConfig.BASE_URL}/auth/logout") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
            }.status.value in 200..300
            if (response) {
                settings.putString("TOKEN", "")
                user = null
                userIsLogged = false
                ApiConfig.userToken = ""
                return true
            }
        } catch (e: Exception) {
            throw e
        }
        return false
    }
}

