package useCase

import data.repository.ImageUploadRepository
import domain.model.DealModel
import domain.model.DealsModel
import domain.model.ImageModel
import domain.repository.DealRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.settings

class DealsUseCase : KoinComponent {
    private val repository: DealRepository by inject()
    private val imageRepository = ImageUploadRepository()
    suspend fun getDeals(
        query: String = "",
        page: Int = 1,
        limit: Int = 20,
    ): DealsModel? {
        try {
            return repository.getDeals(
                query = query,
                page = page,
                limit = limit,
            )

        } catch (e: Exception) {
            throw Exception("Oh, something went wrong!: ${e.message}")
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
            throw Exception("oh, something went wrong! ${e.message}")
        }
    }

    suspend fun uploadImage(
        imageModel: ImageModel,
        imagePaths: (String) -> Unit,
    ) {
        try {
            imageRepository.uploadImage(imageModel,
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
            throw Exception("oh, something went wrong! ${e.message}")
        }
    }

    suspend fun deleteDeal(id: String) {
        repository.deleteDeal(id)
    }
}