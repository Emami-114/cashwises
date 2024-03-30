package ui.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ui.category.viewModel.CategoryEvent
import ui.category.viewModel.CategoryViewModel
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomImagePicker
import ui.components.CustomSwitch
import ui.components.CustomTextField


@Composable
fun CreateCategory() {
    val viewModel: CategoryViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomBackgroundView()
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp).padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CustomImagePicker { image ->
                viewModel.doChangeImageModel(image)
            }
            CustomTextField(
                value = uiState.title,
                onValueChange = { viewModel.onEvent(CategoryEvent.OnTitleChange(it)) },
                placeholder = "Title"
            )
            CustomSwitch(title = "Published", value = uiState.published) {
                viewModel.onEvent(CategoryEvent.OnPublishedChange(it))
            }
            CustomButton(title = "Create Category") {
                viewModel.onEvent(CategoryEvent.OnCreateCategory)
            }
        }
    }
}