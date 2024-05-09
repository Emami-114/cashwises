package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import compose.icons.TablerIcons
import compose.icons.tablericons.CircleCheck
import compose.icons.tablericons.CircleX
import kotlinx.coroutines.delay
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_green_dark
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder

@Composable
fun CustomToast(
    modifier: Modifier = Modifier.zIndex(1f),
    status: NotificationBarEnum = NotificationBarEnum.SUCCESS,
    title: String = "Successfully",
    delay: Long = 1500L,
    onClose: () -> Unit = {}
) {
    var snackBarVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        delay(100)
        snackBarVisible = true
        delay(delay)
        snackBarVisible = false
        onClose()
    }
    AnimatedVisibility(
        snackBarVisible, modifier = modifier,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        },
        exit = slideOutVertically {
            with(density) { 40.dp.roundToPx() }
        } + shrinkVertically(targetHeight = { with(density) { 40.dp.roundToPx() } })
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(40.dp)
                .customBorder()
                .background(
                    brush = if (status == NotificationBarEnum.SUCCESS) {
                        Brush.horizontalGradient(
                            listOf(
                                cw_dark_green_dark.copy(alpha = 0.6f),
                                cw_dark_onBackground,
                                cw_dark_onBackground,
                            ), tileMode = TileMode.Repeated
                        )
                    } else {
                        Brush.horizontalGradient(
                            listOf(
                                cw_dark_red.copy(alpha = 0.6f),
                                cw_dark_onBackground,
                                cw_dark_onBackground,
                            ), tileMode = TileMode.Repeated
                        )
                    },
                    shape = MaterialTheme.shapes.large
                ).padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (status == NotificationBarEnum.SUCCESS) {
                Icon(
                    TablerIcons.CircleCheck,
                    contentDescription = null,
                    tint = cw_dark_green
                )
            } else {
                Icon(
                    TablerIcons.CircleX,
                    contentDescription = null,
                    tint = cw_dark_red
                )
            }
            Text(title, color = cw_dark_whiteText)
            Spacer(modifier.size(20.dp))


        }
    }
}

enum class NotificationBarEnum {
    SUCCESS, ERROR
}