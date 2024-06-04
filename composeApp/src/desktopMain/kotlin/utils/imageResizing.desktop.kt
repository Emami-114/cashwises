package utils

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.plugins.jpeg.JPEGImageWriteParam

actual fun resizeImage(
    imageData: ByteArray,
    width: Int,
    height: Int,
    quality: Int,
): ByteArray {
    val inputStream = ByteArrayInputStream(imageData)
    val originalImage = ImageIO.read(inputStream)

    val resizedImage = BufferedImage(width, height, originalImage.type)
    val g: Graphics2D = resizedImage.createGraphics()
    g.drawImage(originalImage, 0, 0, width, height, null)
    g.dispose()

    val byteArrayOutputStream = ByteArrayOutputStream()
    val writer = ImageIO.getImageWritersByFormatName("jpeg").next()
    val ios = ImageIO.createImageOutputStream(byteArrayOutputStream)
    writer.output = ios
    val jpegParams = writer.defaultWriteParam as JPEGImageWriteParam
    jpegParams.compressionMode = ImageWriteParam.MODE_EXPLICIT
    jpegParams.compressionQuality = quality / 100.0f

    writer.write(null, IIOImage(resizedImage, null, null), jpegParams)
    writer.dispose()

    return byteArrayOutputStream.toByteArray()
}