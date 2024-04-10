package ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    title: String = "",
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    val height = 30.dp
    val width = 60.dp
    val sizePx = with(LocalDensity.current) { (width - height).toPx() }
    val spaceSizePx = with(LocalDensity.current) { (6.dp - 3.dp).toPx() }
    val animatedFloat by animateFloatAsState(
        if (value) sizePx else spaceSizePx,
        animationSpec = tween(200, easing = LinearEasing)
    )
    val animatedColor by animateColorAsState(
        targetValue = if (value) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurface,
        animationSpec = tween(600)
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(9f)
        )
        Box(modifier = Modifier.weight(2f)) {
            Row(
                modifier = Modifier.height(height)
                    .width(width)
                    .clip(RoundedCornerShape(height))
                    .background(animatedColor)
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
}