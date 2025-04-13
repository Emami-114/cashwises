package useCase

import data.model.DealModel
import data.model.DealsQuery
import data.repository.ImageUploadRepository
import domain.model.DealDetailModel
import domain.model.ImageModel
import domain.repository.DealRepository
import domain.repository.Result
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DealsUseCase : KoinComponent {
    private val repository: DealRepository by inject()
    private val imageRepository = ImageUploadRepository()
    suspend fun getDeals(
        query: DealsQuery
    ): Flow<Result<List<DealModel>>> {
        try {
            return repository.getDeals(
                query = query
            )

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getSingleDeal(id: String): Flow<Result<DealDetailModel?>> {
        try {
            return repository.getSingleDeal(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addDeals(
        dealModel: DealDetailModel,
        onSuccess: () -> Unit
    ) {
        try {
            repository.addDeal(dealModel).let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteDeal(deal: DealDetailModel, onSuccess: () -> Unit) {
        try {
            if (deal.imagesUrl != null) {
                deal.imagesUrl.forEach { imagePath ->
                    imageRepository.deleteImage(imagePath)
                }
            }
            if (deal.thumbnailUrl != null) {
                imageRepository.deleteImage(path = deal.thumbnailUrl)
            }
            repository.deleteDeal(deal.id ?: "").let { isSuccess ->
                if (isSuccess) {
                    onSuccess()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}