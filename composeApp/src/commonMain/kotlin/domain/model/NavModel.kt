package domain.model

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object SearchRoute

@Serializable
object ProfileRoute

@Serializable
object NotificationsRoute

@Serializable
object AccountRoute

@Serializable
object SettingsRoute

@Serializable
object AuthenticationRoute

@Serializable
object CreateDealRoute

@Serializable
object WishlistRoute

@Serializable
object ImprintRoute

@Serializable
data class DetailRoute(val id: String)

@Serializable
data class SearchResultRoute(
    val categoryId: String? = null,
    val tag: String? = null,
    val title: String? = null,
    val searchText: String? = null
)