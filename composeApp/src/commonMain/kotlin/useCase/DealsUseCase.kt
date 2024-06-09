package useCase

import data.model.DealsQuery
import data.repository.ImageUploadRepository
import domain.model.DealModel
import domain.model.DealsModel
import domain.model.ImageModel
import domain.repository.DealRepository
import domain.repository.Results
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DealsUseCase : KoinComponent {
    private val repository: DealRepository by inject()
    private val imageRepository = ImageUploadRepository()
    suspend fun getDeals(
        query: DealsQuery
    ): Flow<Results<DealsModel>> {
        try {
            return repository.getDeals(
                query = query
            )

        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getSingleDeal(id: String): Flow<Results<DealModel?>> {
        try {
            return repository.getSingleDeal(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadImages(
        imagesModel: List<ImageModel>,
        imagePaths: (List<String>) -> Unit,
    ) {
        try {
            imageRepository.uploadImages(imagesModel,
                subDir = "deals_images",
                imagePaths = { paths ->
                    if (paths.isNotEmpty()) {
                        imagePaths(paths)
                    } else {
                        println("Failed upload image")
                        throw Exception("oh, something went wrong!")
                    }
                })
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadImage(
        imageModel: ImageModel,
        imagePaths: (String) -> Unit,
    ) {
        try {
            imageRepository.uploadImage(imageModel,
                subDir = "deals_thumbnail",
                imagePath = { paths ->
                    if (paths.isNotEmpty()) {
                        imagePaths(paths)
                    } else {
                        println("Failed upload image")
                        throw Exception("oh, something went wrong!")
                    }
                })
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addDeals(
        dealModel: DealModel,
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

    suspend fun deleteDeal(deal: DealModel, onSuccess: () -> Unit) {
        try {
            if (deal.images != null) {
                deal.images.forEach { imagePath ->
                    imageRepository.deleteImage(imagePath)
                }
            }
            if (deal.thumbnail != null) {
                imageRepository.deleteImage(path = deal.thumbnail)
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