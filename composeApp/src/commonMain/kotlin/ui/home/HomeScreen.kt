package ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.seiko.imageloader.rememberImagePainter
import domain.model.exampleDeals
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.components.ProductRow
import ui.deals.DealsViewModel

class HomeScreen : Screen {
    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel: DealsViewModel = koinInject()
        val uiState by viewModel.state.collectAsState()

        Scaffold(topBar = {
            CustomTopAppBar(title = "Home")
        }, bottomBar = {
            Spacer(modifier = Modifier.height(70.dp))
        }) {

//            FilePicker(show = showFilePicker, fileExtensions = fileType) {
//
//                viewModel.bytes.value = it
//                showFilePicker = false
//                println("imagePath: ${it?.path}")
//            }
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                val scope = this
                val maxWidth = scope.maxWidth
                val column =
                    if (maxWidth > 1150.dp) 5 else if (maxWidth > 900.dp && maxWidth < 1150.dp) 4 else if (maxWidth > 700.dp && maxWidth < 900.dp) 3 else 2

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(column),
                    verticalItemSpacing = 5.dp,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(it)
                        .padding(all = 5.dp)
                ) {
                    items(uiState.deals) { deal ->
                        ProductRow(deal)
                    }
                }
            }
        }
    }
}