package ui.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ui.components.CustomTextField


@Composable
fun CreateCategory() {
    var title by remember { mutableStateOf("") }
    var published by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            CustomTextField(value = title, onValueChange = { title = it })
            Switch(checked = published, onCheckedChange = { published = it })

        }
    }
}