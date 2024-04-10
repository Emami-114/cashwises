package theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    val view = LocalView.current
    LaunchedEffect(isDark) {
        val window = (view.context as Activity).window
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = isDark
            isAppearanceLightNavigationBars = isDark
        }
    }
//    val view = LocalView.current
//    val systemBarColor = Color.TRANSPARENT
//    LaunchedEffect(isDark) {
//        val window = (view.context as Activity).window
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.statusBarColor = systemBarColor
//        window.navigationBarColor = systemBarColor
//        window.decorView.apply {
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//
//        }
//        WindowCompat.getInsetsController(window, window.decorView).apply {
//            isAppearanceLightStatusBars = isDark
//            isAppearanceLightNavigationBars = isDark
//        }
//    }
}