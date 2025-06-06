package ui.components.customModiefier

import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.company.app.theme.cw_dark_borderColor

@Composable
fun Modifier.customBorder(
    color: Color = cw_dark_borderColor,
    width: Dp = 1.dp,
    shape: Shape = MaterialTheme.shapes.large
): Modifier = composed {
    border(width = width, color = color, shape = shape)
}