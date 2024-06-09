package domain.enums

import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bad_request_error
import cashwises.composeapp.generated.resources.conflict_error
import cashwises.composeapp.generated.resources.no_internet_connection
import cashwises.composeapp.generated.resources.not_found_error
import cashwises.composeapp.generated.resources.request_timeout
import cashwises.composeapp.generated.resources.unauthorized_error
import cashwises.composeapp.generated.resources.unknown_error
import org.jetbrains.compose.resources.StringResource

enum class ErrorType(val message: StringResource) {
    NoInternetConnection(Res.string.no_internet_connection),
    RequestTimeout(Res.string.request_timeout),
    UnknownError(Res.string.unknown_error),
    BadRequest(Res.string.bad_request_error),
    Unauthorized(Res.string.unauthorized_error),
    Forbidden(Res.string.unknown_error),
    NotFound(Res.string.not_found_error),
    Conflict(Res.string.conflict_error),
}