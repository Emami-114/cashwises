package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import kotlinx.datetime.Instant
import org.company.app.theme.cw_dark_whiteText
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomImagePicker
import ui.components.CustomMultipleImagePicker
import ui.components.CustomPopUp
import ui.components.CustomRichTextEditor
import ui.components.CustomSwitch
import ui.components.CustomTextField
import ui.deals.ViewModel.DealEvent
import ui.deals.ViewModel.DealsViewModel
import ui.deals.components.MainAndSubCategoryList


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateDealView(modifier: Modifier = Modifier) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    val richTextState = rememberRichTextState()
    val scrollState = rememberScrollState(0)
    val dataPickerState = rememberDatePickerState()

    when {
        uiState.error != null -> {
            CustomPopUp(present = true, onDismissDisable = true, message = uiState.error ?: "")
        }

        uiState.dealCreatedSuccess -> {
            viewModel.onEvent(DealEvent.OnSetDefaultState)
        }

        else -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomBackgroundView()

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
                            .height(400.dp)
                    )

                    CustomTextField(
                        value = uiState.title,
                        onValueChange = { viewModel.onEvent(DealEvent.OnTitleChange(it)) },
                        placeholder = "Title",
                        modifier = Modifier
                    )

                    CustomTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onEvent(DealEvent.OnDescriptionChange(it)) },
                        placeholder = "Description",
                        modifier = Modifier
                    )
                    MainAndSubCategoryList(
                        uiState = uiState,
                        selectedCategories = uiState.category ?: listOf(),
                        onSelected = {
                            viewModel.onEvent(DealEvent.OnCategoryChange(it))
                        },
                    )
                    CustomSwitch(title = "Free", value = uiState.isFree) {
                        viewModel.onEvent(DealEvent.OnIsFreeChange(it))
                    }
                    CustomSwitch(title = "Published", value = uiState.published) {
                        viewModel.onEvent(DealEvent.OnPublishedChange(it))
                    }
                    CustomTextField(
                        value = uiState.price ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnPriceChange(it)) },
                        placeholder = "Price",
                        enabled = !uiState.isFree,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .alpha(if (uiState.isFree) 0.4f else 1f)
                    )

                    CustomTextField(
                        value = uiState.offerPrice ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnOfferPriceChange(it)) },
                        placeholder = "Offer price",
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number
                        ), enabled = !uiState.isFree,
                        modifier = Modifier
                            .alpha(if (uiState.isFree) 0.4f else 1f)

                    )

                    CustomTextField(
                        value = uiState.provider ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnProviderChange(it)) },
                        placeholder = "Provider",
                        modifier = Modifier
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
                        modifier = Modifier
                    )

                    CustomTextField(
                        value = uiState.videoUrl ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnVideoUrlChange(it)) },
                        placeholder = "Video url",
                        modifier = Modifier
                    )
                    DatePicker(
                        state = dataPickerState,
                        showModeToggle = true,
                        dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                        colors = DatePickerDefaults.colors(),
                        title = null
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
                            val date = dataPickerState.selectedDateMillis?.let { dateMillis ->
                                Instant.fromEpochMilliseconds(dateMillis).toString()
                            }
                            viewModel.onEvent(DealEvent.OnExpirationDateChange(date))
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
    }
}


