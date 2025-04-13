package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TagModel(
    val id: String? = null,
    val title: String,
)

var tags = listOf(
    "Elektronik",
    "Gaming",
    "Lebensmittel & Haushalt",
    "Fashion & Accessoires",
    "Beauty & Gesundheit",
    "Family & Kids",
    "Sport & Fitness",
    "Bücher & Filme",
    "Garten & Baumarkt",
    "Auto & Motorrad",
    "Kultur & Freizeit",
    "Telefon- & Internet-Verträge",
    "Versicherung & Finanzen",
    "Urlaub & Reisen"
)