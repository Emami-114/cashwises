import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.getSharedModules
import org.koin.core.context.GlobalContext.startKoin
import ui.App


fun main() = application {
    startKoin { modules(getSharedModules()) }

    Window(onCloseRequest = ::exitApplication, title = "cashwises") {
        CompositionLocalProvider(
//            LocalImageLoader provides remember { generateImageLoader() },
        ) {

            App()
        }
    }
}

