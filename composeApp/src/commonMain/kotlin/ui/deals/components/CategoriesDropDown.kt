package ui.deals.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Checkbox
import compose.icons.tablericons.ChevronDown
import compose.icons.tablericons.ChevronUp
import compose.icons.tablericons.Square
import domain.model.CategoryStatus
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable
import ui.deals.ViewModel.DealsState

@Composable
fun MainAndSubCategoryList(
    uiState: DealsState,
    selectedCategories: List<String>,
    onSelected: (List<String>) -> Unit,
) {
    val selectedItems =
        remember { mutableStateListOf<String>().apply { addAll(selectedCategories) } }
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
                    text = "Categories",
                    color = MaterialTheme.colorScheme.secondary
                )
                Icon(
                    if (isExpanded) TablerIcons.ChevronUp else
                        TablerIcons.ChevronDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            AnimatedVisibility(isExpanded) {
                val filterMainCategory =
                    uiState.categories.filter { it.status == CategoryStatus.MAIN }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    filterMainCategory.forEach { category ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .height(35.dp)
                                .clickable {
                                    if (selectedItems.contains(category.id)) {
                                        selectedItems.remove(category.id)
                                    } else {
                                        selectedItems.add(category.id ?: "")
                                    }
                                    onSelected(selectedItems.toList())
                                }.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            val icon =
                                if (selectedItems.contains(category.id)) TablerIcons.Checkbox else TablerIcons.Square
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                            Text(category.title ?: "", color = MaterialTheme.colorScheme.secondary)
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                        val subCatFilter = uiState.categories.filter { category.id == it.mainId }
                        subCatFilter.forEach { subCat ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .height(35.dp)
                                    .clickable {
                                        if (selectedItems.contains(subCat.id)) {
                                            selectedItems.remove(subCat.id)
                                        } else {
                                            selectedItems.add(subCat.id ?: "")
                                        }
                                        onSelected(selectedItems.toList())
                                    }.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Spacer(modifier = Modifier.width(10.dp))
                                val icon =
                                    if (selectedItems.contains(subCat.id)) TablerIcons.Checkbox else TablerIcons.Square
                                Icon(
                                    icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondary
                                )
                                Text(
                                    subCat.title ?: "",
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                        }
                    }
                }
            }
        }
    }
}