package utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
actual fun resizeImage(
    imageData: ByteArray,
    width: Int,
    height: Int,
    quality: Int
): ByteArray {
    val data = imageData.toNSData()
    val image = UIImage(data = data)

    val jpegData = UIImageJPEGRepresentation(image, quality / 100.0)
    val jpegImage = UIImage(data = jpegData ?: data)

    val size = CGSizeMake(width.toDouble(), height.toDouble())
    UIGraphicsBeginImageContextWithOptions(size, false, 0.0)
    jpegImage.drawInRect(CGRectMake(0.0, 0.0, width.toDouble(), height.toDouble()))
    val newImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    val resizedJpegData = UIImageJPEGRepresentation(newImage ?: UIImage(), quality / 100.0)
    return resizedJpegData?.toByteArray() ?: ByteArray(0)
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toNSData(): NSData {
    return this.usePinned {
        NSData.create(bytes = it.addressOf(0), length = this.size.toULong())
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val data = ByteArray(this.length.toInt())
    this.bytes?.let {
        memcpy(data.refTo(0), it, this.length)
    }
    return data
}