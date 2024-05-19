package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class DealsQuery(
    val page: Int? = null,
    val limit: Int? = null,
    @SerialName("query")
    val searchQuery: String? = null,
    @SerialName("tags")
    val filterTags: List<String>? = null,
    val categories: List<String>? = null
)