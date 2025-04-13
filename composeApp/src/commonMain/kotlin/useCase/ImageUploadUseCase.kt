package useCase

import data.repository.ImageUploadRepository
import domain.model.ImageModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageUploadUseCase : KoinComponent {
    private val repository: ImageUploadRepository = ImageUploadRepository()

    suspend fun uploadDealImage(
        images: List<ImageModel>,
        imagePaths: (List<String>) -> Unit
    ) {
        try {
            repository.uploadImages(
                imagesModel = images,
                bucketName = "deals",
                imagePaths = { paths ->
                    if (paths.isNotEmpty()) {
                        imagePaths(paths)
                    }
                }
            )
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadThumbnailImage(
        image: ImageModel,
        imagePath: (String) -> Unit
    ) {
        try {
            repository.uploadThumbnail(
                imageModel = image,
                bucketName = "thumbnails",
                imagePath = { path ->
                    if (path.isNotEmpty()) {
                        imagePath(path)
                    }
                })
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun uploadCategoryImage(
        image: ImageModel,
        imagePath: (String) -> Unit
    ) {
        try {
            repository.uploadThumbnail(
                imageModel = image,
                bucketName = "categories",
                imagePath = { path ->
                    if (path.isNotEmpty()) {
                        imagePath(path)
                    }
                })
        } catch (e: Exception) {
            throw e
        }
    }
}