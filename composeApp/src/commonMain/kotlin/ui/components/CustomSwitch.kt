package ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    title: String = "",
    value: Boolean,
    errorText: String? = null,
    onValueChange: (Boolean) -> Unit
) {
    val height = 30.dp
    val width = 55.dp
    val sizePx = with(LocalDensity.current) { (width + 2.dp - height).toPx() }
    val spaceSizePx = with(LocalDensity.current) { (6.dp - 2.dp).toPx() }
    val animatedFloat by animateFloatAsState(
        if (value) sizePx else spaceSizePx,
        animationSpec = tween(200, easing = LinearEasing)
    )
    val animatedColor by animateColorAsState(
        targetValue = if (value) cw_dark_primary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(600)
    )
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(8f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier.height(height)
                        .width(width)
                        .clip(RoundedCornerShape(height))
                        .background(animatedColor)
                        .customBorder(color = if (errorText != null) Color.Red else cw_dark_borderColor)
                        .noRippleClickable {
                            onValueChange(!value)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(x = animatedFloat.toInt(), y = 0) }
                            .size(height - 4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                if (value) MaterialTheme.colorScheme.secondary else
                                    MaterialTheme.colorScheme.onSecondary
                            )
                    )
                }
            }
        }
        if (errorText != null) {
            Spacer(modifier.height(5.dp))
            Text(errorText, style = MaterialTheme.typography.labelSmall, color = cw_dark_red)
        }
    }
}