package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DealModel(
    val id: String?,
    val title: String,
    val url: String?,
    val image: String?,
    val price: Double?,
    val discount: Double?,
    val provider: String?,
    val completionDate: String?,
    val createdAt: String,
    val updatedAt: String
)

val exampleDeals = listOf<DealModel>(
    DealModel(
        id = "1",
        title = "Apple TV+ zwei Monate kostenlos*",
        url = "https://redeem.services.apple/de-de/messi-sp-emeia-2024",
        image = "drawable/image1.png",
        price = 19.98,
        discount = 0.0,
        provider = "Check24",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
    DealModel(
        id = "2",
        title = "iPhone 15, 128GB, schwarz - Amazon Prime",
        url = "https://www.amazon.de/dp/B0CHXFCYCR?smid=A3JWKAKR8XB7XF&tag=pepperugc09-21&ascsubtag=1907446144",
        image = "drawable/image2.png",
        price = 789.0,
        discount = 0.0,
        provider = "Amazon",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
    DealModel(
        id = "3",
        title = "(PickSport) Mammut Ultimate III Low GTX Wanderschuhe (42 bis 46)",
        url = "https://www.picksport.de/herren-multifunktionsschuhe-ultimate-iii-low-gtx-men-3030-04660-0001?number=",
        image = "drawable/image3.png",
        price = 74.79,
        discount = 0.0,
        provider = "PickSport",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
    DealModel(
        id = "4",
        title = "SIHOO Ergonomischer Bürostuhl mit Lordosenstütze [AMAZON BLITZANGEBOT] (+3,5% Payback)",
        url = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
        image = "drawable/image4.png",
        price = 89.0,
        discount = 0.0,
        provider = "Sihoo",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),

    DealModel(
        id = "5",
        title = "Jack & Jones Tim Original JOS 719 für 22,45€ / Jack & Jones Tim Original AM 781 ebenfalls 22,45€ (Prime)",
        url = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
        image = "drawable/image5.png",
        price = 22.45,
        discount = 0.0,
        provider = "Amazon",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
    DealModel(
        id = "6",
        title = "(PickSport) Mammut Ultimate III Low GTX Wanderschuhe (42 bis 46)",
        url = "https://www.picksport.de/herren-multifunktionsschuhe-ultimate-iii-low-gtx-men-3030-04660-0001?number=",
        image = "drawable/image6.png",
        price = 74.79,
        discount = 0.0,
        provider = "PickSport",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
    DealModel(
        id = "7",
        title = "SIHOO Ergonomischer Bürostuhl mit Lordosenstütze [AMAZON BLITZANGEBOT] (+3,5% Payback)",
        url = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
        image = "drawable/image7.png",
        price = 89.0,
        discount = 0.0,
        provider = "Sihoo",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),

    DealModel(
        id = "8",
        title = "Jack & Jones Tim Original JOS 719 für 22,45€ / Jack & Jones Tim Original AM 781 ebenfalls 22,45€ (Prime)",
        url = "https://www.amazon.de/dp/B0899LSG8X?smid=A2IL0P6POUUI1H&tag=pepperegc0f-21&ascsubtag=1907448053",
        image = "drawable/image8.png",
        price = 22.45,
        discount = 0.0,
        provider = "Amazon",
        completionDate = "10.03.2024",
        createdAt = "10.03.2024",
        updatedAt = "10.03.2024"
    ),
)