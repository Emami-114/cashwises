package ui.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.successfully_copied
import cashwises.composeapp.generated.resources.successfully_created
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronDown
import compose.icons.tablericons.ChevronUp
import compose.icons.tablericons.X
import domain.model.CategoryModel
import domain.model.CategoryStatus
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import ui.category.viewModel.CategoryEvent
import ui.category.viewModel.CategoryState
import ui.category.viewModel.CategoryViewModel
import ui.components.CustomButton
import ui.components.CustomImagePicker
import ui.components.CustomPopUp
import ui.components.CustomSwitch
import ui.components.CustomTextField
import ui.components.CustomToast
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable


@Composable
fun CreateCategory(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    uiState: CategoryState,
) {

    when {
        uiState.errorText != null -> {
            CustomPopUp(present = true, onDismissDisable = true, message = uiState.errorText)
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.createdSuccessfully || uiState.updateSuccessfully) {
                    CustomToast(title = stringResource(if (uiState.createdSuccessfully) Res.string.successfully_created else Res.string.successfully_copied)) {
                        viewModel.onEvent(CategoryEvent.OnDefaultState)
                    }
                }

                Column(
                    modifier = modifier.fillMaxSize()
                        .padding(10.dp).padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    CustomImagePicker(selectedImage = uiState.imageModel) { image ->
                        viewModel.doChangeImageModel(image)
                    }
                    CustomTextField(
                        value = uiState.title ?: "",
                        onValueChange = { viewModel.onEvent(CategoryEvent.OnTitleChange(it)) },
                        placeholder = "Title"
                    )
                    MainCategoryList(uiState, onSelected = { category ->
                        viewModel.onEvent(CategoryEvent.OnSelectedCatChange(category))
                    })
                    CustomSwitch(
                        textView = { Text("Published", color = cw_dark_whiteText) },
                        value = uiState.published ?: false
                    ) {
                        viewModel.onEvent(CategoryEvent.OnPublishedChange(it))
                    }
                    CustomButton(title = "Create Category") {
                        viewModel.onEvent(CategoryEvent.OnCreateCategory)
                    }
                }
            }
        }
    }
}

@Composable
fun MainCategoryList(
    uiState: CategoryState, onSelected: (CategoryModel?) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.large
            )
            .customBorder()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .noRippleClickable { isExpanded = !isExpanded },
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = uiState.selectedCategory?.title ?: "Categories",
                    color = MaterialTheme.colorScheme.secondary,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (uiState.selectedCategory != null) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Icon(
                            TablerIcons.X,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(20.dp).noRippleClickable { onSelected(null) }
                        )
                    }
                    Icon(
                        if (isExpanded) TablerIcons.ChevronUp else TablerIcons.ChevronDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

            }
            AnimatedVisibility(isExpanded) {
                val filterCategory = uiState.categories.filter { it.status == CategoryStatus.MAIN }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(filterCategory) { category ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .height(42.dp)
                                .clickable {
                                    onSelected(category)
                                    isExpanded = false
                                }.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(category.title ?: "", color = MaterialTheme.colorScheme.secondary)
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    }
                }
            }
        }
    }
}

