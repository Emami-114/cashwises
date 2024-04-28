package domain.enums

sealed interface HttpError {
    data class BadRequest(val value: String) : HttpError
    data class Unauthorized(val value: String) : HttpError
    data class Forbidden(val value: String) : HttpError
    data class NotFound(val value: String) : HttpError
    data class Conflict(val value: String) : HttpError

}