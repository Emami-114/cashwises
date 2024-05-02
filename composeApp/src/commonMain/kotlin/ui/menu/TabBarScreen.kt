package ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import compose.icons.TablerIcons
import compose.icons.tablericons.Edit
import compose.icons.tablericons.LayoutGrid
import compose.icons.tablericons.LayoutGridAdd
import compose.icons.tablericons.Plus
import org.company.app.theme.cw_dark_background
import org.koin.compose.koinInject
import ui.category.CategoriesView
import ui.category.CreateCategory
import ui.category.viewModel.CategoryViewModel
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.components.customModiefier.noRippleClickable
import ui.deals.CreateDealView
import ui.menu.components.TabBarItem
import ui.navigation.RouterBackNavigate

@Composable
fun TabBarScreen(onNavigate: (String) -> Unit) {
    var currentItem by remember { mutableStateOf(TabItemEnum.CREATE_DEAL) }
    var isExpanded by remember { mutableStateOf(false) }
    val categoriesViewModel: CategoryViewModel = koinInject()
    val categoriesUiState by categoriesViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            CustomTopAppBar(title = "", backButtonAction = {
                onNavigate(RouterBackNavigate.Back.route)
            }, rightAction = {
                Icon(
                    if (isExpanded) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.noRippleClickable { isExpanded = !isExpanded })
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingInner ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(paddingInner)) {
            CustomBackgroundView()
            val scope = this
            val maxWidth = scope.maxWidth
            val maxHeight = scope.maxHeight
            if (maxWidth < 800.dp) {
                AnimatedVisibility(
                    visible = isExpanded,
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                        .background(cw_dark_background)
                        .zIndex(1f),
                    enter = slideInHorizontally(initialOffsetX = { -it / 2 }) + fadeIn(
                        animationSpec = tween(100)
                    ),
                    exit = slideOutHorizontally(targetOffsetX = { -it / 2 }) + fadeOut(
                        animationSpec = tween(100)
                    )
                ) {
                    TabBarView(currentItem, modifier = Modifier) { tabItem ->
                        currentItem = tabItem
                        isExpanded = false
                    }
                }
            }
            Row(modifier = Modifier.fillMaxSize()) {
                if (maxWidth >= 800.dp) {
                    TabBarView(currentItem, modifier = Modifier.weight(3f)) { tabItem ->
                        currentItem = tabItem
                        isExpanded = false
                    }
                }
                VerticalDivider()
                when (currentItem) {
                    TabItemEnum.CREATE_DEAL -> {
                        CreateDealView(modifier = Modifier.weight(7f))
                    }

                    TabItemEnum.CATEGORIES -> {
                        CategoriesView(
                            viewModel = categoriesViewModel,
                            uiState = categoriesUiState,
                            modifier = Modifier.weight(6.7f),
                            onClick = {
                                currentItem = TabItemEnum.CREATE_CATEGORY
                            }
                        )
                    }

                    TabItemEnum.CREATE_CATEGORY -> {
                        AnimatedVisibility(
                            currentItem == TabItemEnum.CREATE_CATEGORY,
                            modifier = Modifier.weight(6.7f),
                        ) {
                            CreateCategory(
                                viewModel = categoriesViewModel,
                                uiState = categoriesUiState,
                            )
                        }
                    }

                    TabItemEnum.EDIT_DEAL -> {}
                    TabItemEnum.EDIT_CATEGORY -> {}
                }
            }
        }
    }
}

@Composable
fun TabBarView(
    currentItem: TabItemEnum,
    modifier: Modifier = Modifier,
    selectedItem: (TabItemEnum) -> Unit
) {
    Column(
        modifier = modifier.padding(10.dp).padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        TabBarItem(
            title = TabItemEnum.CREATE_DEAL.title,
            icon = TabItemEnum.CREATE_DEAL.icon,
            currentItem = currentItem.title
        ) {
            selectedItem(TabItemEnum.CREATE_DEAL)
        }
        TabBarItem(
            title = TabItemEnum.CATEGORIES.title,
            icon = TabItemEnum.CATEGORIES.icon,
            currentItem = currentItem.title
        ) {
            selectedItem(TabItemEnum.CATEGORIES)
        }

        TabBarItem(
            title = TabItemEnum.CREATE_CATEGORY.title,
            icon = TabItemEnum.CREATE_CATEGORY.icon,
            currentItem = currentItem.title
        ) {
            selectedItem(TabItemEnum.CREATE_CATEGORY)
        }
    }
}

enum class TabItemEnum(var title: String, var icon: ImageVector) {
    CREATE_DEAL(title = "Create Deal", icon = TablerIcons.Plus),
    CATEGORIES(title = "Categories", icon = TablerIcons.LayoutGrid),
    CREATE_CATEGORY(title = "Create Category", icon = TablerIcons.LayoutGridAdd),
    EDIT_DEAL(title = "Edit Deal", icon = TablerIcons.Edit),
    EDIT_CATEGORY(title = "Edit Category", icon = TablerIcons.Edit);

}
