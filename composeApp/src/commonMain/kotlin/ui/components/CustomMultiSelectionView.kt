package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.TagModel
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomMultiSelection(
    modifier: Modifier = Modifier,
    tags: List<TagModel> = listOf(),
    selectedTags: List<String> = listOf(),
    onSearch: (String) -> Unit,
    onSelected: (List<String>) -> Unit
) {
    val selectedItems =
        remember { mutableStateListOf<String>().apply { addAll(selectedTags) } }
    var query by remember { mutableStateOf("") }
    Box(
        modifier = modifier.fillMaxWidth()
            .height(400.dp)
            .background(cw_dark_onBackground, shape = MaterialTheme.shapes.large)
            .customBorder()
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text("Tags:", color = cw_dark_grayText, fontSize = 20.sp)
            HorizontalDivider()
            CustomSearchView(
                value = query,
                onValueChange = {
                    query = it
                    onSearch(it)
                },
                onFocused = {},
                onSearchClick = {},
                animation = false
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(4),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tags) { tag ->
                    Box(
                        modifier = Modifier
                            .background(
                                if (selectedItems.contains(tag.title)) cw_dark_green else cw_dark_background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .customBorder(shape = MaterialTheme.shapes.medium)
                            .noRippleClickable {
                                if (selectedItems.contains(tag.title)) {
                                    selectedItems.remove(tag.title)
                                    onSelected(selectedItems.toList())
                                } else {
                                    selectedItems.add(tag.title)
                                    onSelected(selectedItems.toList())
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            tag.title,
                            color = cw_dark_whiteText,
                            modifier = Modifier.padding(5.dp),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}