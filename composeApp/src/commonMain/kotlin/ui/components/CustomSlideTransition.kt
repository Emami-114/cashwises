package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import ui.account.auth.AuthView
import ui.menu.MenuBarEnum

@Composable
fun CustomSlideTransition(
    visible: Boolean,
    currentView: @Composable () -> Unit,
    slideView: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = !visible,
        enter = slideInHorizontally(
            initialOffsetX = { -30 }, // small slide 300px
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing // interpolator
            )
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { -30 },
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing
            )
        ) + fadeOut()
    ) {
        currentView()
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 200, easing = LinearEasing)
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(200, easing = LinearEasing)
        ) + fadeOut()
    ) {
        slideView()
    }
}