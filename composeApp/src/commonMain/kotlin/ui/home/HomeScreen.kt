package ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import cashwises.composeapp.generated.resources.*
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.category_deals
import cashwises.composeapp.generated.resources.compose_multiplatform
import domain.model.CreateDealRoute
import domain.model.DetailRoute
import org.company.app.theme.md_theme_dark_primary
import org.company.app.theme.md_theme_dark_secondary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.components.*
import ui.components.customModiefier.noRippleClickable
import ui.customNavigate
import ui.deals.DealsView
import ui.deals.ViewModel.DealsViewModel

@Composable
fun HomeView(navController: NavHostController) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getCategories()
        viewModel.getDeals()
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            HomeTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                selectedItem = selectedCategory,
                isExpanded = uiState.isItemExpanded,
                onExpanded = {
                    viewModel.doChangeExpandedItem()
                },
                onClick = { viewModel.doChangeSelectedCategory(it) })
        },
        floatingActionButton = {
            Button(
                modifier = Modifier.padding(bottom = 60.dp),
                onClick = {
                    navController.customNavigate(CreateDealRoute)
                }) {
                Image(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValue ->
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackgroundView()
            DealsView(
                modifier = Modifier.padding(top = paddingValue.calculateTopPadding() - 20.dp),
                viewModel = viewModel,
                uiState = uiState,
                isExpanded = uiState.isItemExpanded,
                navController = navController
            )
        }
    }
}

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    selectedItem: String,
    isExpanded: Boolean = false,
    onExpanded: () -> Unit,
    onClick: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val items = listOf<String>(
        stringResource(Res.string.category_deals),
        stringResource(Res.string.category_free),
        stringResource(Res.string.category_mobile_tariffs)
    )


    Column(modifier = Modifier.height(150.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
            )
            Image(
                painterResource(if (isExpanded) Res.drawable.layout_grid else Res.drawable.list),
                contentDescription = null,
                modifier = Modifier.size(25.dp).noRippleClickable {
                    onExpanded()
                },
                colorFilter = ColorFilter.tint(color = md_theme_dark_secondary)
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { item ->
                val color by animateColorAsState(
                    targetValue = if (item == selectedItem) md_theme_dark_primary else Color.Transparent,
                    animationSpec = tween(delayMillis = 80, easing = EaseInOut)
                )
                Text(
                    text = item,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = md_theme_dark_secondary,
                    modifier = Modifier
                        .background(
                            color = color,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .noRippleClickable {
                            onClick(item)
                        }
                        .padding(vertical = 3.dp, horizontal = 6.dp)
                )
            }
        }
        CustomDivider(modifier = Modifier.zIndex(1f))
    }
}