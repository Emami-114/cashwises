package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import org.koin.compose.koinInject
import ui.components.CustomButton
import ui.components.CustomImagePicker
import ui.components.CustomMultipleImagePicker
import ui.components.CustomRichTextEditor
import ui.components.CustomTextField
import ui.components.CustomTopAppBar
import ui.deals.ViewModel.DealEvent
import ui.deals.ViewModel.DealsViewModel
import ui.deals.components.MainAndSubCategoryList


class CreateDealScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CustomTopAppBar(title = "", backButtonAction = {
                    navigator.pop()
                })
            }
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val scope = this
                val maxWidth = scope.maxWidth
                val maxHeight = scope.maxHeight
                if (maxWidth > 800.dp) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CreateDealView(modifier = Modifier.weight(1f))
                        CreateDealView(modifier = Modifier.weight(1f))
                    }
                } else {
                    CreateDealView(modifier = Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateDealView(modifier: Modifier = Modifier) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    val richTextState = rememberRichTextState()
    val scrollState = rememberScrollState(0)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        CustomBackgroundView()

        FlowRow(
            modifier = Modifier.padding(10.dp).padding(horizontal = 10.dp)
                .verticalScroll(state = scrollState, true),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            maxItemsInEachRow = 3
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                CustomImagePicker(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    selectedImage = uiState.thumbnailByte
                ) { image ->
                    viewModel.doChangeThumbnail(image)
                }
            }

            CustomRichTextEditor(
                state = richTextState,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
            )

            CustomTextField(
                value = uiState.title,
                onValueChange = { viewModel.onEvent(DealEvent.OnTitleChange(it)) },
                placeholder = "Title",
                modifier = Modifier.weight(1f)
            )

            CustomTextField(
                value = uiState.description,
                onValueChange = { viewModel.onEvent(DealEvent.OnDescriptionChange(it)) },
                placeholder = "Description",
                modifier = Modifier.weight(1f)
            )
            MainAndSubCategoryList(
                uiState = uiState,
                selectedCategories = uiState.category ?: listOf(),
                onSelected = {
                    viewModel.onEvent(DealEvent.OnCategoryChange(it))
                },
            )
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Switch(checked = uiState.isFree ?: false,
                    onCheckedChange = { viewModel.onEvent(DealEvent.OnIsFreeChange(it)) })
                Switch(checked = uiState.published ?: false,
                    onCheckedChange = { viewModel.onEvent(DealEvent.OnPublishedChange(it)) })
            }

            CustomTextField(
                value = uiState.price ?: "",
                onValueChange = { viewModel.onEvent(DealEvent.OnPriceChange(it)) },
                placeholder = "Price",
                enabled = uiState.isFree != true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
                    .alpha(if (uiState.isFree == true) 0.4f else 1f)
            )

            CustomTextField(
                value = uiState.offerPrice ?: "",
                onValueChange = { viewModel.onEvent(DealEvent.OnOfferPriceChange(it)) },
                placeholder = "Offer price",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number
                ), enabled = uiState.isFree != true,
                modifier = Modifier.weight(1f)
                    .alpha(if (uiState.isFree == true) 0.4f else 1f)

            )

            CustomTextField(
                value = uiState.provider ?: "",
                onValueChange = { viewModel.onEvent(DealEvent.OnProviderChange(it)) },
                placeholder = "Provider",
                modifier = Modifier.weight(1f)
            )

            CustomTextField(
                value = uiState.providerUrl ?: "",
                onValueChange = { viewModel.onEvent(DealEvent.OnProviderUrlChange(it)) },
                placeholder = "provider url",
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Uri
                ),
                modifier = Modifier.weight(1f)
            )

            CustomTextField(
                value = uiState.videoUrl ?: "",
                onValueChange = { viewModel.onEvent(DealEvent.OnVideoUrlChange(it)) },
                placeholder = "Video url",
                modifier = Modifier.weight(1f)
            )
            CustomMultipleImagePicker(
                modifier = Modifier,
                selectedImage = uiState.imagesByte ?: listOf()
            ) { images ->
                viewModel.doChangeImages(images)
            }
            CustomButton(
                isLoading = uiState.isLoading,
                onClick = {
                    viewModel.onEvent(DealEvent.OnDescriptionChange(richTextState.toHtml()))
                    viewModel.onEvent(DealEvent.OnAction)
                    richTextState.clear()
                },
                title = "Create",
                modifier = Modifier.fillMaxWidth().height(50.dp)
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}


