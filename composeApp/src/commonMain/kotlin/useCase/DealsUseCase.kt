package useCase

import domain.model.DealsModel
import domain.repository.DealRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DealsUseCase : KoinComponent {
    private val repository: DealRepository by inject()

    suspend fun getDeals(): DealsModel {
        return repository.getDeals()
    }

    suspend fun addDeals(
        title: String,
        description: String,
        category: String?,
        isFree: Boolean?,
        price: Double?,
        offerPrice: Double?,
        published: Boolean?,
        expirationDate: String?,
        provider: String?,
        providerUrl: String?,
        thumbnail: String?,
        images: List<String>?
    ) {
        repository.addDeal(
            title = title,
            description = description,
            category = category,
            isFree = isFree,
            price = price,
            offerPrice = offerPrice,
            published = published,
            expirationDate = expirationDate,
            provider = provider,
            providerUrl = providerUrl,
            images = images,
            thumbnail = thumbnail
        )
    }

    suspend fun uploadImage(
        byteArray: ByteArray,
        fileName: String,
        path: (String) -> Unit
    ): Boolean {
        return repository.uploadImage(byteArray, fileName, path = {
            path(it)
        })
    }

    suspend fun deleteDeal(id: String) {
        repository.deleteDeal(id)
    }
}