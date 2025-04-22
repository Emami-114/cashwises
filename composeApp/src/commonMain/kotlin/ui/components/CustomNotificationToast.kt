package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.checkbox
import cashwises.composeapp.generated.resources.x
import kotlinx.coroutines.delay
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import ui.components.customModiefier.customBorder

@Composable
fun CustomNotificationToast(
    modifier: Modifier = Modifier.zIndex(1f),
    status: ToastStatusEnum = ToastStatusEnum.SUCCESS,
    title: String = "Successfully",
    delay: Long = 1500L,
    isAvailableBottomBar: Boolean = true,
    onClose: () -> Unit = {}
) {
    var snackBarVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        delay(120)
        snackBarVisible = true
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        delay(delay)
        snackBarVisible = false
        onClose()
    }
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().zIndex(3f)
    ) {
        AnimatedVisibility(
            snackBarVisible,
            modifier = modifier.align(Alignment.BottomStart)
//                .padding(bottom = if (isAvailableBottomBar) 50.dp else 20.dp)
            ,
            enter = slideInVertically {
                with(density) { 60.dp.roundToPx() }
            },
            exit = slideOutVertically {
                with(density) { 60.dp.roundToPx() }
            } + shrinkVertically(targetHeight = { with(density) { 60.dp.roundToPx() } })
        ) {

            Row(
                modifier = modifier.fillMaxWidth().zIndex(4f)
                    .padding(horizontal = 20.dp)
                    .clip(MaterialTheme.shapes.large)
                    .customBorder(shape = MaterialTheme.shapes.large)
                    .background(
                        if (status == ToastStatusEnum.SUCCESS) {
                            cw_dark_green
                        } else {
                            cw_dark_red
                        }
                    )
                    .padding(top = 7.dp)
                    .height(50.dp)
                    .background(
                        color = cw_dark_onBackground,
                    ).padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (status == ToastStatusEnum.SUCCESS) {
                    Icon(
                        painter = painterResource(Res.drawable.checkbox),
                        contentDescription = null,
                        tint = cw_dark_green,
                        modifier = Modifier.size(26.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.x),
                        contentDescription = null,
                        tint = cw_dark_red,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Text(title, color = cw_dark_whiteText)
                Spacer(modifier.size(20.dp))
            }
        }
    }
}

enum class ToastStatusEnum {
    SUCCESS, ERROR, Warning
}