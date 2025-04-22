package utils

import androidx.compose.ui.interop.UIKitView
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return

    UIApplication.sharedApplication.openURL(
        nsUrl,
        options = emptyMap<Any?, Any>(),
        completionHandler = null
    )
}