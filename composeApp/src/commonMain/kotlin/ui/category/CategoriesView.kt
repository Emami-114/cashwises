package ui.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import compose.icons.TablerIcons
import compose.icons.tablericons.Edit
import data.repository.ApiConfig
import domain.model.CategoryModel
import domain.model.CategoryStatus
import ui.category.viewModel.CategoryEvent
import ui.category.viewModel.CategoryState
import ui.category.viewModel.CategoryViewModel
import ui.components.CustomDivider
import ui.components.CustomPopUp
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CategoriesView(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    uiState: CategoryState,
    onClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    when {
        uiState.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorText != null -> {
            CustomPopUp(
                present = true,
                onDismissDisable = false,
                message = uiState.errorText,
                cancelAction = {
                    viewModel.onEvent(CategoryEvent.OnDefaultState)
                })
        }

        else -> {
            Box(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categories.filter { it.status == CategoryStatus.MAIN }) { category ->
                        CategoryItem(uiState, category) {
                            uiState.isUpdate = true
                            viewModel.onEvent(CategoryEvent.OnTitleChange(category.title ?: ""))
                            viewModel.onEvent(
                                CategoryEvent.OnPublishedChange(
                                    category.published ?: false
                                )
                            )
                            onClick()
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun CategoryItem(
    uiState: CategoryState,
    categoryModel: CategoryModel,
    onClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .customBorder()
        .background(
            if (isExpanded) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
            shape = MaterialTheme.shapes.large
        )
        .padding(5.dp)
        .noRippleClickable {
            isExpanded = !isExpanded
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryItemHeader(categoryModel) { onClick() }
            AnimatedVisibility(isExpanded) {
                CustomDivider()
                val filterSubCat = uiState.categories.filter { it.mainId == categoryModel.id }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    filterSubCat.onEachIndexed { _, categoryModel ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.width(30.dp))
                            CategoryItemHeader(categoryModel, height = 40.dp) { onClick() }
                        }
                        HorizontalDivider()
                    }
                }
            }

        }
    }
}

@Composable
fun CategoryItemHeader(
    categoryModel: CategoryModel,
    height: Dp = 65.dp,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(end = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(height)
                    .customBorder()
                    .clip(shape = MaterialTheme.shapes.large),
                model = "${ApiConfig.BASE_URL}/images/${categoryModel.thumbnail}",
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onError = {},
                onLoading = {},
            )
            Text(
                categoryModel.title ?: "",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                TablerIcons.Edit,
                contentDescription = null,
                modifier = Modifier.noRippleClickable(onEdit),
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier.size(20.dp).padding(end = 10.dp)
                    .background(
                        shape = MaterialTheme.shapes.large,
                        color = if (categoryModel.published == true) Color.Green else Color.Gray
                    )
            )
        }
    }
}