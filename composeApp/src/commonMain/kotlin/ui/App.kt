package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirectiveWithTwoPanesOnMediumWidth
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.russhwolf.settings.Settings
import data.repository.ApiConfig
import data.repository.UserRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import okio.FileSystem
import theme.AppTheme
import ui.components.CustomNotificationToast
import utils.LocalPushNotification
import utils.Utils
import utils.decodeJWT

var settings = Settings()

@Composable
fun App() = AppTheme {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.captionBar)
            .background(
                MaterialTheme.colorScheme.background
            ),
    ) {
        Utils.showNotification = { status, message, isAvailableBottomBar ->
            CustomNotificationToast(
                status = status,
                title = message,
                isAvailableBottomBar = isAvailableBottomBar,
                onClose = {})
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(Unit) {
                LocalPushNotification.requestAuthorization {}
                UserRepository.INSTANCE.getMe()
                println("is jwt valid: ${Utils.isJwtTokenValid()}")
                Utils.isJwtTokenValid()
            }
            HomeNav()
        }
    }

}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(800L * 800 * 800) // 512MB
        .build()
}