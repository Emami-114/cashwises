package ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomTextView(
    text: String,

    ) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary
    )
}
