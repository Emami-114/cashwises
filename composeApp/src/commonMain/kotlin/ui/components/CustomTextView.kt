package ui.components

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
