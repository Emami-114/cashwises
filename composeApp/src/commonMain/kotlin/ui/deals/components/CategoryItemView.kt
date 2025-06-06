package ui.deals.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import data.repository.ApiConfig
import domain.model.CategoryModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoryItemView(
    modifier: Modifier = Modifier,
    categoryModel: CategoryModel,
    onSearch: (CategoryModel) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().noRippleClickable {
            onSearch(categoryModel)
        },
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize()
                .shadow(3.dp, shape = MaterialTheme.shapes.large)
                .customBorder()
                .clip(MaterialTheme.shapes.large)
                .weight(8f),
            model = "${ApiConfig.IMAGE_URL}/${categoryModel.thumbnail}",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            onError = {},
            onLoading = {},
        )
        categoryModel.title?.let { title ->
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally).weight(2f)
            )
        }
    }
}