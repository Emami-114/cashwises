package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
            initialOffsetX = { -300 }, // small slide 300px
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing // interpolator
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -300 },
            animationSpec = tween(
                durationMillis = 200,
                easing = LinearEasing
            )
        )
    ) {
        currentView()
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 200, easing = LinearEasing)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(200, easing = LinearEasing)
        )
    ) {
        slideView()
    }
}