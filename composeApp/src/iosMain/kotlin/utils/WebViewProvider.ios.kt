package utils

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIApplication
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun WebPageViewer(url: String) {
    val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    val webView = WKWebView(
        frame = viewController!!.view.bounds,
        configuration = WKWebViewConfiguration()
    )
    val urlRequest = NSURLRequest(uRL = NSURL(string = url))
    webView.loadRequest(urlRequest)
    viewController.view.addSubview(webView)
}