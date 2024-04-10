package domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.absoluteValue

@Serializable
data class DealsModel(
    val deals: List<DealModel>
)

@Serializable
data class DealModel(
    val id: String? = null,
    val title: String,
    val description: String,
    val category: List<String>? = null,
    @SerialName("is_free")
    val isFree: Boolean? = null,
    val price: Double? = null,
    @SerialName("offer_price")
    val offerPrice: Double? = null,
    val published: Boolean? = null,
    @SerialName("expiration_date")
    val expirationDate: String? = null,
    val provider: String? = null,
    @SerialName("provider_url")
    val providerUrl: String? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("video_url")
    val videoUrl: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
) {
    val currentTime = Clock.System.now().daysUntil(Instant.parse(createdAt ?: ""), timeZone = TimeZone.UTC).absoluteValue
}

//val exampleDeals = listOf<DealModel>(
//    DealModel(
//        id = "1",
//        title = "Apple TV+ zwei Monate kostenlos*",
//        providerUrl = "https://redeem.services.apple/de-de/messi-sp-emeia-2024",
//        thumbnail = "drawable/image1.png",
//        price = 19.98,
//        offerPrice = 0.0,
//        provider = "Check24",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = "",
//
//        ),
//    DealModel(
//        id = "2",
//        title = "iPhone 15, 128GB, schwarz - Amazon Prime",
//        providerUrl = "https://www.amazon.de/dp/B0CHXFCYCR?smid=A3JWKAKR8XB7XF&tag=pepperugc09-21&ascsubtag=1907446144",
//        thumbnail = "drawable/image2.png",
//        price = 789.0,
//        offerPrice = 0.0,
//        provider = "Amazon",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//    DealModel(
//        id = "3",
//        title = "(PickSport) Mammut Ultimate III Low GTX Wanderschuhe (42 bis 46)",
//        providerUrl = "https://www.picksport.de/herren-multifunktionsschuhe-ultimate-iii-low-gtx-men-3030-04660-0001?number=",
//        thumbnail = "drawable/image3.png",
//        price = 74.79,
//        offerPrice = 0.0,
//        provider = "PickSport",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//    DealModel(
//        id = "4",
//        title = "SIHOO Ergonomischer Bürostuhl mit Lordosenstütze [AMAZON BLITZANGEBOT] (+3,5% Payback)",
//        providerUrl = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
//        thumbnail = "drawable/image4.png",
//        price = 89.0,
//        offerPrice = 0.0,
//        provider = "Sihoo",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//
//    DealModel(
//        id = "5",
//        title = "Jack & Jones Tim Original JOS 719 für 22,45€ / Jack & Jones Tim Original AM 781 ebenfalls 22,45€ (Prime)",
//        providerUrl = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
//        thumbnail = "drawable/image5.png",
//        price = 22.45,
//        offerPrice = 0.0,
//        provider = "Amazon",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//    DealModel(
//        id = "6",
//        title = "(PickSport) Mammut Ultimate III Low GTX Wanderschuhe (42 bis 46)",
//        providerUrl = "https://www.picksport.de/herren-multifunktionsschuhe-ultimate-iii-low-gtx-men-3030-04660-0001?number=",
//        thumbnail = "drawable/image6.png",
//        price = 74.79,
//        offerPrice = 0.0,
//        provider = "PickSport",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//    DealModel(
//        id = "7",
//        title = "SIHOO Ergonomischer Bürostuhl mit Lordosenstütze [AMAZON BLITZANGEBOT] (+3,5% Payback)",
//        providerUrl = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
//        thumbnail = "drawable/image7.png",
//        price = 89.0,
//        offerPrice = 0.0,
//        provider = "Sihoo",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//
//    DealModel(
//        id = "8",
//        title = "Jack & Jones Tim Original JOS 719 für 22,45€ / Jack & Jones Tim Original AM 781 ebenfalls 22,45€ (Prime)",
//        providerUrl = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
//        thumbnail = "drawable/image8.png",
//        price = 22.45,
//        offerPrice = 0.0,
//        provider = "Amazon",
//        expirationDate = "10.03.2024",
//        createdAt = "10.03.2024",
//        updatedAt = "10.03.2024",
//        published = false,
//        isFree = false,
//        category = "",
//        description = ""
//    ),
//)