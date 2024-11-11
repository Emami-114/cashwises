package ui.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.create_deal
import cashwises.composeapp.generated.resources.create_notification
import cashwises.composeapp.generated.resources.registration_required
import cashwises.composeapp.generated.resources.registration_required_desc
import cashwises.composeapp.generated.resources.registration_required_notification
import cashwises.composeapp.generated.resources.required_select_notification
import cashwises.composeapp.generated.resources.settings
import data.repository.UserRepository
import domain.model.tags
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomSelectionView
import ui.components.CustomTopAppBar
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable
import ui.settings
import useCase.TagsUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(onNavigate: (String) -> Unit) {
    val viewModel: NotificationViewModel = koinInject()
    val localTags by viewModel.localTags.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        println("localTags: ${localTags.count()}")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopAppBar(title = "", rightAction = {
            Image(
                painter = painterResource(Res.drawable.settings),
                contentDescription = null,
                colorFilter = ColorFilter.tint(cw_dark_whiteText),
                modifier = Modifier.size(26.dp).noRippleClickable {
                    scope.launch {
                        showBottomSheet = true
                    }
                }
            )
        })
        when {
            UserRepository.INSTANCE.user == null -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(Res.string.registration_required_notification),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = cw_dark_whiteText
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        stringResource(Res.string.registration_required_desc),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = cw_dark_whiteText
                    )
                }
            }

            viewModel.errorString.value != null -> {
                CustomPopUp(
                    present = viewModel.errorString.value != null,
                    message = viewModel.errorString.value ?: "",
                    cancelAction = {
                        viewModel.errorString.value = null
                    }
                )
            }

            viewModel.isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }


            else -> {
                if (localTags.count() < 2 && !showBottomSheet) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(Res.string.required_select_notification),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = cw_dark_whiteText,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        CustomButton(
                            modifier = Modifier.height(50.dp),
                            title = stringResource(Res.string.create_notification)
                        ) {
                            showBottomSheet = true
                        }
                    }
                }
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        }, sheetState = sheetState,
                        dragHandle = {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(50.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    ) {
                        SelectNotificationView(viewModel, onNavigate)
                    }
                }
            }
        }
    }
}

@Composable
fun SelectNotificationView(viewModel: NotificationViewModel, onNavigate: (String) -> Unit) {
    val tagsModels by viewModel.tagsModel.collectAsState()
    val localTags by viewModel.localTags.collectAsState()
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 2),
            verticalItemSpacing = 10.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(all = 10.dp)
        ) {
            items(tagsModels) { tag ->
                CustomSelectionView(
                    text = tag,
                    isSelected = localTags.contains(tag)
                ) {
                    viewModel.addTagToLocal(tag)
                    viewModel.storeTagsLocal()
                }
            }
        }
    }
}

class NotificationViewModel : ViewModel(), KoinComponent {
    var tagsModel = MutableStateFlow<List<String>>(listOf())
    var localTags = MutableStateFlow<List<String>>(listOf())
    private val tagsUseCase: TagsUseCase by inject()
    var isLoading = mutableStateOf(false)
    var errorString = mutableStateOf<String?>(null)

    init {
        getTags()
        getTagsLocal()
    }

    fun storeTagsLocal() = viewModelScope.launch {
        settings.putString("user_tags", localTags.value.joinToString(","))
        getTagsLocal()
    }

    fun addTagToLocal(tag: String) = viewModelScope.launch {
        val tags = localTags.value.toMutableList()
        if (tags.contains(tag)) {
            tags.remove(tag)
            localTags.emit(tags)
        } else {
            tags.add(tag)
            localTags.emit(tags)
        }
    }

    private fun getTagsLocal() = viewModelScope.launch {
        val allStoredTags = settings.getString("user_tags", defaultValue = "")
        val parts = allStoredTags.split(",")
        localTags.emit(parts)
    }

    private fun getTags() = viewModelScope.launch {
        tagsModel.emit(tags)
//        tagsUseCase.getTags(null).collectLatest { status ->
//            when (status) {
//                is Results.Loading -> {
//                    isLoading.value = true
//                }
//
//                is Results.Success -> {
//                    tagsModel.emit(status.data ?: listOf())
//                    errorString.value = null
//                    isLoading.value = false
//                }
//
//                is Results.Error -> {
//                    errorString.value = getString(status.error?.message!!)
//                }
//            }
//        }
    }
}