package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class DealsQuery(
    val page: Int? = null,
    val limit: Int? = null,
    val searchTerm: String? = null,
    val tag: String? = null,
    val category: String? = null
)