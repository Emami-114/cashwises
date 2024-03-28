package ui.deals

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImagePainter
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import com.seiko.imageloader.ui.AutoSizeImage
import data.repository.ApiConfig
import domain.model.DealModel
import kotlinx.coroutines.launch
import ui.components.CustomButton
import ui.components.CustomTopAppBar

class DetailDealScreen(val dealModel: DealModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(modifier = Modifier.fillMaxWidth(),
                    title = dealModel.title, backButtonAction = {
                        navigator.pop()
                    })
            }
        ) { paddingValue ->
            DetailDealView(dealModel = dealModel, paddingValue)
        }
    }

}

@Composable
fun DetailDealView(dealModel: DealModel, paddingValues: PaddingValues) {
    val richTextState = rememberRichTextState()
    val scrollState = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        richTextState.setHtml(dealModel.description)
    }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    ) {
        val scope = this
        val width = scope.maxWidth
        val height = scope.maxHeight

        Column(
            modifier = Modifier.padding(paddingValues)
                .verticalScroll(scrollState)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    }).padding(10.dp).padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            val imageUrl = "${ApiConfig.BASE_URL}image/${dealModel.thumbnail}"
            AutoSizeBox(
                url = imageUrl,
                modifier = Modifier.fillMaxWidth()
            ) { imageAction ->
                when (imageAction) {
                    is ImageAction.Success -> {
                        Image(
                            rememberImageSuccessPainter(imageAction),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(if (width < 900.dp) 0.6f else 0.8f)
                                .clip(MaterialTheme.shapes.large)
                        )
                    }

                    is ImageAction.Loading -> {}
                    is ImageAction.Failure -> {}
                }
            }
            RichTextEditor(
                state = richTextState,
                readOnly = true,
                shape = MaterialTheme.shapes.large
            )

            CustomButton(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                title = "To Deal"
            ) {

            }
        }
    }

}