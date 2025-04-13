package domain.repository

import domain.enums.ErrorType

enum class ResultStatus { SUCCESS, LOADING, ERROR }

sealed class Result<T>(val status: ResultStatus, val data: T? = null, val error: ErrorType? = null) {
    class Loading<T> : Result<T>(ResultStatus.LOADING)
    class Success<T>(data: T?) : Result<T>(ResultStatus.SUCCESS, data = data)
    class Error<T>(error: ErrorType?) : Result<T>(ResultStatus.ERROR, error = error)
}