package ui.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.customModiefier.customBorder

@Composable
fun TabBarItem(
    title: String,
    icon: ImageVector,
    currentItem: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(60.dp)
            .background(
                if (currentItem == title) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.large
            )
            .customBorder()
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() }
            .padding(5.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(30.dp)
        )
        Text(
            title,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 18.sp
        )
    }
}