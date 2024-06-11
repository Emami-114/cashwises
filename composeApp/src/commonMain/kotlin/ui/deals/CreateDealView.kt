package ui.deals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.coupon_code
import cashwises.composeapp.generated.resources.create_deal
import cashwises.composeapp.generated.resources.description
import cashwises.composeapp.generated.resources.free
import cashwises.composeapp.generated.resources.offer_price
import cashwises.composeapp.generated.resources.price
import cashwises.composeapp.generated.resources.provider
import cashwises.composeapp.generated.resources.provider_url
import cashwises.composeapp.generated.resources.publish
import cashwises.composeapp.generated.resources.shipping_costs
import cashwises.composeapp.generated.resources.successfully_created
import cashwises.composeapp.generated.resources.title
import cashwises.composeapp.generated.resources.video_url
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_blackText
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomImagePicker
import ui.components.CustomMultiSelection
import ui.components.CustomMultipleImagePicker
import ui.components.CustomPopUp
import ui.components.CustomRichTextEditor
import ui.components.CustomSwitch
import ui.components.CustomTextField
import ui.components.CustomToast
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
    val dataPickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val timePickerState = rememberTimePickerState(is24Hour = true)
    val textQuery by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.getCategories()
        viewModel.getTags()
    }

    when {
        uiState.error != null -> {
            CustomPopUp(present = true, onDismissDisable = true, message = uiState.error ?: "")
        }

        else -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomBackgroundView()
                if (uiState.dealCreatedSuccess) {
                    CustomToast(title = stringResource(Res.string.successfully_created)) {
                        viewModel.onEvent(DealEvent.OnSetDefaultState)
                    }
                }

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
                        placeholder = stringResource(Res.string.title),
                        modifier = Modifier
                    )

                    CustomTextField(
                        value = uiState.description,
                        onValueChange = { viewModel.onEvent(DealEvent.OnDescriptionChange(it)) },
                        placeholder = stringResource(Res.string.description),
                        modifier = Modifier
                    )
                    MainAndSubCategoryList(
                        uiState = uiState,
                        selectedCategories = uiState.category ?: listOf(),
                        onSelected = {
                            viewModel.onEvent(DealEvent.OnCategoryChange(it))
                        },
                    )
                    CustomSwitch(
                        textView = {
                            Text(
                                stringResource(Res.string.free),
                                color = cw_dark_whiteText
                            )
                        },
                        value = uiState.isFree
                    ) {
                        viewModel.onEvent(DealEvent.OnIsFreeChange(it))
                    }
                    CustomSwitch(
                        textView = {
                            Text(
                                stringResource(Res.string.publish),
                                color = cw_dark_whiteText
                            )
                        },
                        value = uiState.published
                    ) {
                        viewModel.onEvent(DealEvent.OnPublishedChange(it))
                    }
                    CustomTextField(
                        value = uiState.price ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnPriceChange(it)) },
                        placeholder = stringResource(Res.string.price),
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
                        placeholder = stringResource(Res.string.offer_price),
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
                        placeholder = stringResource(Res.string.provider),
                        modifier = Modifier
                    )

                    CustomTextField(
                        value = uiState.providerUrl ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnProviderUrlChange(it)) },
                        placeholder = stringResource(Res.string.provider_url),
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
                        placeholder = stringResource(Res.string.video_url),
                        modifier = Modifier
                    )

                    CustomTextField(
                        value = uiState.couponCode ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnCouponCodeChange(it)) },
                        placeholder = stringResource(Res.string.coupon_code),
                        modifier = Modifier
                    )
                    CustomMultiSelection(
                        tags = uiState.listTag,
                        selectedTags = uiState.selectedTags,
                        onSearch = {
                            viewModel.getTags(it)
                        },
                        onSelected = {
                            viewModel.onEvent(DealEvent.OnTagsChange(it))
                            println("test list tags $it")

                        }
                    )

                    CustomTextField(
                        value = "${uiState.shippingCosts ?: 0.0}",
                        onValueChange = { viewModel.onEvent(DealEvent.OnShippingCostChange(it.toDouble())) },
                        placeholder = stringResource(Res.string.shipping_costs),
                        modifier = Modifier,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    DatePicker(
                        state = dataPickerState,
                        showModeToggle = true,
                        dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                        colors = DatePickerDefaults.colors(),
                        title = null
                    )
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerColors(
                            containerColor = cw_dark_onBackground,
                            clockDialColor = cw_dark_onBackground,
                            selectorColor = cw_dark_whiteText,
                            periodSelectorBorderColor = cw_dark_whiteText,
                            clockDialSelectedContentColor = cw_dark_background,
                            clockDialUnselectedContentColor = cw_dark_whiteText,
                            periodSelectorSelectedContainerColor = cw_dark_whiteText,
                            periodSelectorUnselectedContainerColor = cw_dark_background,
                            periodSelectorSelectedContentColor = cw_dark_blackText,
                            periodSelectorUnselectedContentColor = cw_dark_whiteText,
                            timeSelectorSelectedContainerColor = cw_dark_onBackground,
                            timeSelectorUnselectedContainerColor = cw_dark_background,
                            timeSelectorSelectedContentColor = cw_dark_whiteText,
                            timeSelectorUnselectedContentColor = cw_dark_whiteText
                        )

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
                                Instant.fromEpochMilliseconds(dateMillis)
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            }
                            val dateTime = date?.let {
                                LocalDateTime(
                                    it.year,
                                    it.month,
                                    it.dayOfMonth,
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            }
                            val instant = dateTime?.toInstant(TimeZone.UTC).toString()
                            viewModel.onEvent(DealEvent.OnExpirationDateChange(instant))
                            viewModel.onEvent(DealEvent.OnDescriptionChange(richTextState.toHtml()))
                            viewModel.onEvent(DealEvent.OnAction)
                            richTextState.clear()
                        },
                        title = stringResource(Res.string.create_deal),
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    )
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }
    }
}


