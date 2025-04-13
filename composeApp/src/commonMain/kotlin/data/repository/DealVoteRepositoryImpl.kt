package data.repository

import data.repository.ApiConfig.httpClient
import domain.enums.ErrorType
import domain.model.DealVoteModel
import domain.repository.DealVoteRepository
import domain.repository.Result
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException

class DealVoteRepositoryImpl : DealVoteRepository {
    private val client = httpClient

    override suspend fun addDealVote(dealVoteModel: DealVoteModel): Boolean {
        return try {
            client.post("${ApiConfig.BASE_URL}/vote") {
                contentType(ContentType.Application.Json)
                bearerAuth(ApiConfig.userToken)
                setBody(dealVoteModel)
            }.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDealVotesByDealId(dealId: String): Flow<Result<List<DealVoteModel>>> =
        flow {
            emit(Result.Loading())
            try {
                val result = client.get("${ApiConfig.BASE_URL}/vote/$dealId") {
                    bearerAuth(ApiConfig.userToken)
                }.body<List<DealVoteModel>>()
                emit(Result.Success(result))
            } catch (e: Exception) {
                when (e) {
                    is IOException -> emit(Result.Error(ErrorType.NoInternetConnection))
                    is HttpRequestTimeoutException -> emit(Result.Error(ErrorType.RequestTimeout))
                    else -> emit(Result.Error(ErrorType.UnknownError))
                }
            }
        }

    override suspend fun getDealVotesByDealIdAndUserId(
        userId: String,
        dealId: String
    ): Flow<Result<DealVoteModel?>> = flow {
        emit(Result.Loading())
        try {
            val result = client.get("${ApiConfig.BASE_URL}/vote/deal/$dealId/user/$userId") {
                bearerAuth(ApiConfig.userToken)
            }.body<DealVoteModel?>()
            emit(Result.Success(result))
        } catch (e: Exception) {
            when (e) {
                is IOException -> emit(Result.Error(ErrorType.NoInternetConnection))
                is HttpRequestTimeoutException -> emit(Result.Error(ErrorType.RequestTimeout))
                else -> emit(Result.Error(ErrorType.UnknownError))
            }
        }

    }

    override suspend fun deleteDealVoteByDealIdAndUserId(
        dealId: String,
        userId: String
    ): Boolean {
        return try {
            client.delete("${ApiConfig.BASE_URL}/vote/deal/$dealId/user/$userId") {
                bearerAuth(ApiConfig.userToken)
            }.status.value in 200..300
        } catch (e: Exception) {
            throw e
        }
    }
}