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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import cashwises.composeapp.generated.resources.copy
import cashwises.composeapp.generated.resources.external_link
import cashwises.composeapp.generated.resources.free
import cashwises.composeapp.generated.resources.heart
import cashwises.composeapp.generated.resources.heart_fill
import cashwises.composeapp.generated.resources.offers_ends_in_some_day
import cashwises.composeapp.generated.resources.offers_ends_in_some_hour
import cashwises.composeapp.generated.resources.offers_expired
import cashwises.composeapp.generated.resources.some_day_ago
import cashwises.composeapp.generated.resources.some_hour_ago
import cashwises.composeapp.generated.resources.some_minute_ago
import cashwises.composeapp.generated.resources.successfully_copied
import cashwises.composeapp.generated.resources.trash
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import data.repository.UserRepository
import domain.model.DealModel
import domain.model.UserRole
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.until
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_green_dark
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
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
            title = if (scrollState.value >= 800) deal?.title ?: "" else "",
            hasBackground = scrollState.value >= 800,
            textColor = cw_dark_whiteText,
            isDivider = false,
            backButtonAction = {
                onNavigate(AppConstants.BackClickRoute.route)
            },
            rightAction = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(if (isDealMarked) Res.drawable.heart_fill else Res.drawable.heart),
                        contentDescription = null,
                        tint = if (isDealMarked) cw_dark_red else cw_dark_whiteText,
                        modifier = Modifier.size(26.dp).clickable {
                            if (dealId != null) {
                                scopeCoroutine.launch {
                                    UserRepository.INSTANCE.addMarkDealForUser(dealId)
                                        .let { isSuccess ->
                                            if (isSuccess) {
                                                isDealMarked =
                                                    UserRepository.INSTANCE.userMarkedDeals.value.contains(
                                                        dealId
                                                    )
                                            }
                                        }

                                }
                            }
                        })

                    if (UserRepository.INSTANCE.user?.role == UserRole.ADMIN) {
                        Icon(
                            painter = painterResource(Res.drawable.trash),
                            contentDescription = null,
                            tint = cw_dark_whiteText,
                            modifier = Modifier.size(26.dp).clickable {
                                viewModel.deleteDeal(deal)
                                onNavigate(AppConstants.BackClickRoute.route)
                            })
                    }
                }
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
                        painter = painterResource(Res.drawable.external_link),
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

    LaunchedEffect(Unit) {
        richTextState.setHtml(dealModel.description)
    }
    Column(
        modifier = modifier.fillMaxSize().widthIn(min = 300.dp, max = 900.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Box {
            dealModel.thumbnail?.let { _ ->
                CustomImagesSlider(
                    paths = dealModel.images,
                )
                if (dealModel.offerPrice != null) {
                    val offerPercent =
                        (((dealModel.price!! - dealModel.offerPrice) / dealModel.price) * 100).toInt()
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(all = 20.dp)
                            .padding(start = 10.dp)
                            .size(45.dp)
                            .background(cw_dark_primary, shape = CircleShape).padding(5.dp)
                            .zIndex(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${offerPercent}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(dealModel.provider ?: "", fontSize = 12.sp, color = cw_dark_grayText)

//            Text(
//                text = if (dealModel.currentCreatedHour.toInt() == 0) stringResource(
//                    Res.string.some_minute_ago,
//                    "${dealModel.currentCreatedMinute}"
//                )
//                else if (dealModel.currentCreatedDay == 0 && dealModel.currentCreatedHour < 24) stringResource(
//                    Res.string.some_hour_ago,
//                    "${dealModel.currentCreatedHour}"
//                )
//                else if (dealModel.currentCreatedDay > 0) stringResource(
//                    Res.string.some_day_ago,
//                    "${dealModel.currentCreatedDay}"
//                )
//                else "",
//                fontSize = 10.sp,
//                color = cw_dark_grayText,
//                fontWeight = FontWeight.Medium
//            )
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
            val currentExpirationHour = Clock.System.now().until(
                Instant.parse(expirationDate), unit = DateTimeUnit.HOUR
            ).absoluteValue

            val currentExpirationDay = Clock.System.now().daysUntil(
                Instant.parse(expirationDate), timeZone = TimeZone.UTC
            )
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).background(
                    if (currentExpirationDay > 0) cw_dark_green else cw_dark_red.copy(alpha = 0.7f),
                    shape = MaterialTheme.shapes.medium
                ).padding(vertical = 3.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    if (currentExpirationDay == 0 && currentExpirationHour < 24) stringResource(
                        Res.string.offers_ends_in_some_hour,
                        currentExpirationHour
                    )
                    else if (currentExpirationDay < 0) stringResource(Res.string.offers_expired)
                    else if (currentExpirationDay > 0) stringResource(
                        Res.string.offers_ends_in_some_day, currentExpirationDay + 1
                    ) else "",
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
                Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    Text(couponCode, color = cw_dark_whiteText)
                    Icon(
                        painter = painterResource(Res.drawable.copy),
                        contentDescription = null,
                        tint = cw_dark_green_dark,
                        modifier = Modifier.size(26.dp)
                    )
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