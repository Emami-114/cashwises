package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(modifier: Modifier = Modifier,height: Dp = 1.dp, color: Color? = null) {
    if (color != null) {
        Box(
            modifier = modifier.fillMaxWidth().height(height)
                .background(
                    color = color
                )
        )
    } else {
        Box(
            modifier = Modifier.fillMaxWidth().height(height)
                .background(
                    brush = Brush.horizontalGradient(
                        colors =
                            listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.onSecondary,
                                MaterialTheme.colorScheme.onSecondary,
                                MaterialTheme.colorScheme.onSecondary,
                                MaterialTheme.colorScheme.background
                            )
                    )
                )
        )
    }
}