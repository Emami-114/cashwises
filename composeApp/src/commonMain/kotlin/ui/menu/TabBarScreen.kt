package ui.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.categories
import cashwises.composeapp.generated.resources.create_category
import cashwises.composeapp.generated.resources.create_deal
import cashwises.composeapp.generated.resources.create_tags
import cashwises.composeapp.generated.resources.edit
import cashwises.composeapp.generated.resources.edit_category
import cashwises.composeapp.generated.resources.edit_deal
import cashwises.composeapp.generated.resources.layout_grid
import cashwises.composeapp.generated.resources.layout_grid_add
import cashwises.composeapp.generated.resources.plus
import cashwises.composeapp.generated.resources.registration_required
import cashwises.composeapp.generated.resources.registration_required_desc
import data.repository.UserRepository
import domain.model.UserRole
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.AppConstants
import ui.category.CategoriesView
import ui.category.CreateCategory
import ui.category.viewModel.CategoryViewModel
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.components.customModiefier.noRippleClickable
import ui.deals.CreateDealView
import ui.home.tags.CreateTagView
import ui.menu.components.TabBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDealAndCategoriesScreen(onNavigate: (String) -> Unit) {
    var currentItem by remember { mutableStateOf(TabItemEnum.CREATE_DEAL) }
    var isExpanded by remember { mutableStateOf(false) }
    val categoriesViewModel: CategoryViewModel = koinInject()
    val categoriesUiState by categoriesViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            CustomTopAppBar(title = "", backButtonAction = {
                onNavigate(AppConstants.BackClickRoute.route)
            }, rightAction = {
                if (UserRepository.INSTANCE.userIsCreator() || UserRepository.INSTANCE.userIsAdmin())
                Icon(
                    if (isExpanded) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.noRippleClickable { isExpanded = !isExpanded })
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingInner ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            CustomBackgroundView()
            val scope = this
            val maxWidth = scope.maxWidth
            val maxHeight = scope.maxHeight
            if (UserRepository.INSTANCE.userIsCreator() || UserRepository.INSTANCE.userIsAdmin()) {
                if (maxWidth < 800.dp && UserRepository.INSTANCE.userIsAdmin()) {
                    AnimatedVisibility(
                        visible = isExpanded,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(top = paddingInner.calculateTopPadding())
                            .fillMaxHeight()
                            .background(cw_dark_background)
                            .zIndex(1f),
                        enter = slideInHorizontally(
                            initialOffsetX = { -it / 2 },
                            animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                        ) + fadeIn(),
                        exit = slideOutHorizontally(
                            targetOffsetX = { -it / 2 },
                            animationSpec = tween(
                                durationMillis = 200,
                                easing = LinearEasing
                            )
                        ) + fadeOut()
                    ) {
                        TabBarView(currentItem, modifier = Modifier) { tabItem ->
                            currentItem = tabItem
                            isExpanded = false
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxSize().padding(paddingInner)) {
                    if (maxWidth >= 800.dp && UserRepository.INSTANCE.userIsAdmin()) {
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
                        TabItemEnum.CREATE_TAGS -> {
                            CreateTagView()
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(Res.string.registration_required), fontSize = 30.sp, color = cw_dark_whiteText)
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(stringResource(Res.string.registration_required_desc), fontSize = 15.sp, textAlign = TextAlign.Center, color = cw_dark_whiteText)
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
            title = stringResource(TabItemEnum.CREATE_DEAL.title),
            iconResource = TabItemEnum.CREATE_DEAL.icon,
            currentItem = stringResource(currentItem.title)
        ) {
            selectedItem(TabItemEnum.CREATE_DEAL)
        }
        TabBarItem(
            title = stringResource(TabItemEnum.CATEGORIES.title),
            iconResource = TabItemEnum.CATEGORIES.icon,
            currentItem = stringResource(currentItem.title)
        ) {
            selectedItem(TabItemEnum.CATEGORIES)
        }

        TabBarItem(
            title = stringResource(TabItemEnum.CREATE_CATEGORY.title),
            iconResource = TabItemEnum.CREATE_CATEGORY.icon,
            currentItem = stringResource(currentItem.title)
        ) {
            selectedItem(TabItemEnum.CREATE_CATEGORY)
        }

        TabBarItem(
            title = stringResource(TabItemEnum.CREATE_TAGS.title),
            iconResource = TabItemEnum.CREATE_TAGS.icon,
            currentItem = stringResource(currentItem.title)
        ) {
            selectedItem(TabItemEnum.CREATE_TAGS)
        }
    }
}

enum class TabItemEnum(var title: StringResource, var icon: DrawableResource) {
    CREATE_DEAL(title = Res.string.create_deal, icon = Res.drawable.layout_grid),
    CATEGORIES(title = Res.string.categories, icon = Res.drawable.layout_grid),
    CREATE_CATEGORY(title = Res.string.create_category, icon = Res.drawable.layout_grid_add),
    EDIT_DEAL(title = Res.string.edit_deal, icon = Res.drawable.edit),
    EDIT_CATEGORY(title = Res.string.edit_category, icon = Res.drawable.edit),
    CREATE_TAGS(title = Res.string.create_tags, icon = Res.drawable.plus);

}
