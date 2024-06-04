import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.ComposeUIViewController
import ui.App

@OptIn(ExperimentalComposeApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        platformLayers = true
    }
) {
    CompositionLocalProvider(
//        LocalImageLoader provides remember { generateImageLoader() },
    ) {
        App()
    }
}

//fun generateImageLoader(): ImageLoader {
//    return ImageLoader {
//        components {
//            setupDefaultComponents()
//        }
//        interceptor {
//            // cache 32MB bitmap
//            bitmapMemoryCacheConfig {
//                maxSize(32 * 1024 * 1024) // 32MB
//            }
//            // cache 50 image
//            imageMemoryCacheConfig {
//                maxSize(50)
//            }
//            // cache 50 painter
//            painterMemoryCacheConfig {
//                maxSize(50)
//            }
//            diskCacheConfig {
//                directory(getCacheDir().toPath().resolve("image_cache"))
//                maxSizeBytes(512L * 1024 * 1024) // 512MB
//            }
//        }
//    }
//}

//private fun getCacheDir(): String {
//    return NSSearchPathForDirectoriesInDomains(
//        NSCachesDirectory,
//        NSUserDomainMask,
//        true,
//    ).first() as String
//}