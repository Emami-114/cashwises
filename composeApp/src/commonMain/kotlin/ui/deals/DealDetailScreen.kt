package ui.deals

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.free
import cashwises.composeapp.generated.resources.offers_ends_in_some_day
import cashwises.composeapp.generated.resources.offers_ends_today
import cashwises.composeapp.generated.resources.offers_expired
import cashwises.composeapp.generated.resources.some_day_ago
import cashwises.composeapp.generated.resources.successfully_copied
import cashwises.composeapp.generated.resources.today
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import compose.icons.TablerIcons
import compose.icons.tablericons.Bookmark
import compose.icons.tablericons.Copy
import compose.icons.tablericons.ExternalLink
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Scissors
import data.repository.UserRepository
import domain.model.DealModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_green_dark
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_onPrimary
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.AppConstants
import ui.components.CustomBackgroundView
import ui.components.CustomImagesSlider
import ui.components.CustomToast
import ui.components.CustomTopAppBar
import ui.components.ToastStatus
import ui.components.customModiefier.noRippleClickable
import ui.deals.ViewModel.DealsViewModel
import kotlin.math.absoluteValue


@Composable
fun DealDetailScreen(
    dealId: String? = null,
    onNavigate: (String) -> Unit,
) {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    val scrollState = rememberScrollState(0)
    var showToast by remember { mutableStateOf(false) }
    var clipCopyText by remember { mutableStateOf("") }
    val clipBoard = LocalClipboardManager.current
    var deal by remember { mutableStateOf<DealModel?>(null) }
    val scopeCoroutine = rememberCoroutineScope()
    val colorAnimation by animateColorAsState(
        targetValue = if (scrollState.value < 700) cw_dark_background.copy(alpha = 0.03f) else cw_dark_background,
        animationSpec = tween(600)
    )
    var isDealMarked by remember { mutableStateOf(false) }

    LaunchedEffect(dealId) {
        dealId?.let {
            viewModel.doGetSingleDeal(dealId) { dealModel ->
                deal = dealModel
            }
        }
        isDealMarked = UserRepository.INSTANCE.userMarkedDeals.value.contains(dealId)
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val scope = this
        val maxWidth = scope.maxWidth
        CustomBackgroundView()
        CustomTopAppBar(
            modifier = Modifier.align(Alignment.TopStart).zIndex(1f).fillMaxWidth(),
            title = if (scrollState.value < 800) "" else deal?.title ?: "",
            backgroundColor = colorAnimation,
            hasBackButtonBackground = scrollState.value < 800,
            textColor = cw_dark_whiteText,
            isDivider = false,
            backButtonAction = {
                onNavigate(AppConstants.BackClickRoute.route)
            },
            rightAction = {
                Icon(
                    TablerIcons.Bookmark,
                    contentDescription = null,
                    tint = if (isDealMarked) cw_dark_primary else cw_dark_whiteText,
                    modifier = Modifier.size(26.dp).clickable {
                        if (dealId != null) {
                            scopeCoroutine.launch {
                                UserRepository.INSTANCE.addMarkDealForUser(dealId)
                                isDealMarked = UserRepository.INSTANCE.userMarkedDeals.value.contains(dealId)
                            }
                        }
                    }
                )
            },
        )
        deal?.let { deal ->
            DetailDealView(
                modifier = Modifier,
                dealModel = deal,
                scrollState = scrollState,
                clipBoard = { copyText ->
                    clipBoard.setText(annotatedString = AnnotatedString(text = copyText))
                    clipCopyText = copyText
                    showToast = true
                })
        }
        Box(
            modifier = Modifier.height(80.dp).background(cw_dark_background)
                .align(Alignment.BottomStart), contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(45.dp).align(Alignment.TopStart)
                    .padding(top = 5.dp).padding(horizontal = 25.dp)
                    .background(cw_dark_primary, shape = MaterialTheme.shapes.large)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (uiState.selectedDeal?.isFree == true) {
                        Text(
                            text = stringResource(Res.string.free),
                            color = cw_dark_whiteText,
                            fontSize = 17.sp
                        )
                    } else if (uiState.selectedDeal?.offerPrice != null) {
                        Text(
                            text = "${uiState.selectedDeal?.offerPrice ?: 0}€",
                            color = cw_dark_whiteText,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "${uiState.selectedDeal?.price ?: 0}€",
                            color = cw_dark_whiteText,
                            fontSize = 10.sp,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    } else {
                        Text(
                            text = "${uiState.selectedDeal?.price ?: 0}€",
                            color = cw_dark_whiteText,
                            fontSize = 17.sp
                        )
                    }
                    Icon(
                        TablerIcons.ExternalLink,
                        contentDescription = null,
                        tint = cw_dark_whiteText,
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
        }
        if (showToast) {
            CustomToast(
                modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 50.dp),
                status = ToastStatus.SUCCESS,
                title = stringResource(Res.string.successfully_copied, clipCopyText)
            ) { showToast = false }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDealView(
    modifier: Modifier = Modifier,
    dealModel: DealModel,
    scrollState: ScrollState,
    clipBoard: (String) -> Unit
) {
    val richTextState = rememberRichTextState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        richTextState.setHtml(dealModel.description)
    }
    Column(
        modifier = modifier.fillMaxSize().widthIn(min = 300.dp, max = 900.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        dealModel.thumbnail?.let { path ->
            CustomImagesSlider(
                paths = dealModel.images,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(dealModel.provider ?: "", fontSize = 12.sp, color = cw_dark_grayText)
            val currentDate = Clock.System.now().daysUntil(
                Instant.parse(dealModel.createdAt ?: ""), timeZone = TimeZone.UTC
            ).absoluteValue
            Text(
                text = if (currentDate > 0) stringResource(Res.string.some_day_ago, "$currentDate")
                else stringResource(Res.string.today),
                fontSize = 10.sp,
                color = cw_dark_grayText,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            dealModel.title,
            fontSize = 18.sp,
            color = cw_dark_whiteText,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        dealModel.expirationDate?.let { expirationDate ->
            val expiration = Clock.System.now().daysUntil(
                Instant.parse(expirationDate), timeZone = TimeZone.UTC
            )
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).background(
                    if (expiration > 0) cw_dark_green else cw_dark_red.copy(alpha = 0.7f),
                    shape = MaterialTheme.shapes.medium
                ).padding(vertical = 3.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    if (expiration == 0) stringResource(Res.string.offers_ends_today)
                    else if (expiration < 0) stringResource(Res.string.offers_expired)
                    else stringResource(Res.string.offers_ends_in_some_day, expiration),
                    fontSize = 12.sp,
                    color = cw_dark_whiteText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize().padding(bottom = 3.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        dealModel.couponCode?.let { couponCode ->
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                .background(cw_dark_onBackground, shape = MaterialTheme.shapes.large).height(50.dp)
                .drawBehind {
                    val stroke = Stroke(
                        width = 5f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                    drawRoundRect(
                        color = cw_dark_borderColor,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }.noRippleClickable {
                    clipBoard(couponCode)
                }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    TablerIcons.Scissors,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopStart).offset(x = 20.dp, y = (-12).dp),
                    tint = cw_dark_green
                )
                Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    Text(couponCode, color = cw_dark_whiteText)
                    Icon(TablerIcons.Copy, contentDescription = null, tint = cw_dark_green_dark)
                }
            }
        }

        Text(
            "Detail",
            fontSize = 20.sp,
            color = cw_dark_grayText,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).offset(y = 15.dp)
        )
        OutlinedRichTextEditor(
            state = richTextState,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            textStyle = TextStyle(fontSize = 14.sp),
            colors = RichTextEditorDefaults.outlinedRichTextEditorColors(
                containerColor = cw_dark_background,
                textColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(70.dp))
    }
}