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
