package ui.deals

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.rememberImagePainter
import com.seiko.imageloader.rememberImageSuccessPainter
import com.seiko.imageloader.ui.AutoSizeBox
import com.seiko.imageloader.ui.AutoSizeImage
import data.repository.ApiConfig
import domain.model.DealModel
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_blackText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.components.CustomButton
import ui.components.CustomImagesSlider
import ui.components.CustomTopAppBar

class DetailDealScreen(val dealModel: DealModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(bottomBar = {
            CustomButton(
                modifier = Modifier.fillMaxWidth().height(50.dp), title = "To Deal"
            ) {

            }
        }) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val scope = this
                val maxWidth = scope.maxWidth
                CustomTopAppBar(modifier = Modifier.zIndex(1f).fillMaxWidth(),
                    title = dealModel.title,
                    backgroundColor = Color.Transparent,
                    textColor = cw_dark_blackText,
                    isDivider = false,
                    backButtonAction = {
                        navigator.pop()
                    })
                DetailDealView(
                    modifier = Modifier,
                    dealModel = dealModel,
                    paddingValues = PaddingValues(top = 0.dp),
                    maxWidth = maxWidth
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDealView(
    modifier: Modifier = Modifier,
    dealModel: DealModel,
    paddingValues: PaddingValues,
    maxWidth: Dp
) {
    val richTextState = rememberRichTextState()
    val scrollState = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        richTextState.setHtml(dealModel.description)
    }
    Column(
        modifier = modifier
            .padding(paddingValues)
            .verticalScroll(scrollState),
//            .draggable(
//                orientation = Orientation.Vertical,
//                state = rememberDraggableState { delta ->
//                coroutineScope.launch {
//                    scrollState.scrollBy(-delta)
//                }
//            })
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        CustomImagesSlider(thumbnail = dealModel.thumbnail!!, paths = dealModel.images)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedRichTextEditor(
                state = richTextState,
                readOnly = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = RichTextEditorDefaults.outlinedRichTextEditorColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    textColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.surface,
                    disabledBorderColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface
                )
            )
        }
        Spacer(modifier = Modifier.height(70.dp))
    }
}