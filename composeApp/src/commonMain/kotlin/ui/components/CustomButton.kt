package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier.height(50.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    isLoading: Boolean = false,
    title: String,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(color, shape)
            .clickable(enabled = !isLoading) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )

        }
    }
}