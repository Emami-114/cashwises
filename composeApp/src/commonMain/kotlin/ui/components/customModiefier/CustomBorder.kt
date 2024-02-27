package ui.components.customModiefier

import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.customBorder(
    color: Color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
    width: Dp = 0.9.dp,
    shape: Shape = MaterialTheme.shapes.large
): Modifier = composed {
    border(width = width, color = color, shape = shape)
}
