package utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

actual fun resizeImage(
    imageData: ByteArray,
    width: Int,
    height: Int,
    quality: Int
): ByteArray {
    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
    val byteArrayOutputStream = ByteArrayOutputStream()
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG,quality,byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}