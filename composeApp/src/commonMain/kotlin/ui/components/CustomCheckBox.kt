package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Checkbox
import compose.icons.tablericons.Square

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    text: String,
    active: Boolean,
    selected: () -> Unit,
    unselected: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(40.dp)
            .clickable {
                if (active) {
                    unselected()
                } else {
                    selected()
                }
            }.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val icon =
            if (active) TablerIcons.Checkbox else TablerIcons.Square
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Text(text, color = MaterialTheme.colorScheme.secondary)
    }
}