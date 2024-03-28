package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomButton(
    modifier: Modifier = Modifier.padding(horizontal = 15.dp).height(50.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .clip(MaterialTheme.shapes.large).background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}