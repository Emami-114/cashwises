package domain.repository

import domain.enums.ErrorType

enum class ResultStatus { SUCCESS, LOADING, ERROR }

sealed class Results<T>(val status: ResultStatus, val data: T? = null, val error: ErrorType? = null) {
    class Loading<T> : Results<T>(ResultStatus.LOADING)
    class Success<T>(data: T?) : Results<T>(ResultStatus.SUCCESS, data = data)
    class Error<T>(error: ErrorType?) : Results<T>(ResultStatus.ERROR, error = error)
}