package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import domain.model.exampleDeals
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.components.ProductRow

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(topBar = {
            CustomTopAppBar(title = "Home")
        }, bottomBar = {
            Spacer(modifier = Modifier.height(70.dp))
        }) {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 5.dp,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(it)
                        .padding(all = 5.dp)
                ) {
                    items(exampleDeals) { deal ->
                        ProductRow(deal)
                    }
                }
            }

        }

    }

}