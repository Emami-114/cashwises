package ui.deals.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.chevron_down
import cashwises.composeapp.generated.resources.chevron_up
import org.jetbrains.compose.resources.painterResource
import ui.components.CustomCheckBox
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
                    painter = if (isExpanded)
                        painterResource(Res.drawable.chevron_up)
                    else
                        painterResource(Res.drawable.chevron_down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(26.dp)
                )
            }
            AnimatedVisibility(isExpanded) {
                val filterMainCategory =
                    uiState.categories.filter { it.isMainCategory == true }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    filterMainCategory.forEach { category ->
                        CustomCheckBox(
                            text = category.title ?: "",
                            active = selectedItems.contains(category.id),
                            selected = {
                                selectedItems.add(category.id ?: "")
                                onSelected(selectedItems.toList())
                            },
                            unselected = {
                                selectedItems.remove(category.id)
                                onSelected(selectedItems.toList())
                            }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                        val subCatFilter = uiState.categories.filter { it.parentCategoryId == category.id }
                        subCatFilter.forEach { subCat ->
                            Row {
                                Spacer(modifier = Modifier.width(15.dp))
                                CustomCheckBox(
                                    text = subCat.title ?: "",
                                    active = selectedItems.contains(subCat.id),
                                    selected = {
                                        selectedItems.add(subCat.id ?: "")
                                        onSelected(selectedItems.toList())
                                    },
                                    unselected = {
                                        selectedItems.remove(subCat.id)
                                        onSelected(selectedItems.toList())
                                    }
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