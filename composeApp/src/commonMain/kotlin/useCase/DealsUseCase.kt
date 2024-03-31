package useCase

import data.repository.ImageUploadRepository
import domain.model.DealModel
import domain.model.DealsModel
import domain.model.ImageModel
import domain.repository.DealRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DealsUseCase : KoinComponent {
    private val repository: DealRepository by inject()
    private val imageRepository = ImageUploadRepository()
    suspend fun getDeals(): DealsModel? {
        try {
            return repository.getDeals()

        } catch (e: Exception) {
            throw Exception("Oh, something went wrong!: ${e.message}")
        }
    }

    suspend fun uploadImage(
        imagesModel: List<ImageModel>,
        imagePaths: (List<String>) -> Unit,
    ) {
        try {
            imageRepository.uploadImage(imagesModel,
                subDir = "deals_images",
                imagePath = { paths ->
                    if (paths.isNotEmpty()) {
                        imagePaths(paths)
                    } else {
                        println("Failed upload image")
                        throw Exception("oh, something went wrong!")
                    }
                })
        } catch (e: Exception) {
            throw Exception("oh, something went wrong! ${e.message}")
        }
    }

    suspend fun addDeals(
        dealModel: DealModel
    ) {
        repository.addDeal(
            dealModel
        )
    }

    suspend fun deleteDeal(id: String) {
        repository.deleteDeal(id)
    }
}