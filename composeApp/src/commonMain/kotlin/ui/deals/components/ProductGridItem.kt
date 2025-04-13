package ui.deals.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.external_link
import cashwises.composeapp.generated.resources.free
import coil3.compose.AsyncImage
import data.model.DealModel
import data.repository.ApiConfig
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable
import utils.openUrl

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProductGridItem(
    modifier: Modifier = Modifier,
    dealModel: DealModel,
    onNavigateToDetail: () -> Unit,
    onNavigateToProvider: (String) -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(15.dp)
    var imageByte by remember { mutableStateOf(ByteArray(0)) }

    Box(
        modifier = modifier.fillMaxSize()
            .alpha(if (dealModel.hasExpirationDate() && dealModel.expirationDateToDay() < 0) 0.3f else 1f)
            .clip(MaterialTheme.shapes.large), contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.large)) {
                if (dealModel.offerPrice != null) {
                    val offerPercent =
                        (((dealModel.price!! - dealModel.offerPrice) / dealModel.price) * 100).toInt()
                    Box(
                        modifier = Modifier.padding(start = 18.dp).height(25.dp)
                            .background(cw_dark_primary).padding(2.dp).zIndex(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${offerPercent}%",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 130.dp)
                        .clickable { onNavigateToDetail() }
                        .clip(MaterialTheme.shapes.large).customBorder(),
                    model = "${ApiConfig.IMAGE_URL}/${dealModel.thumbnailUrl}",
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    onError = {},
                    onLoading = {},
                )
            }
            Text(
                dealModel.title,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 3,
                color = cw_dark_whiteText,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 7.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dealModel.isFree == true) {
                        Text(
                            text = stringResource(Res.string.free),
                            color = cw_dark_primary,
                            fontSize = 15.sp
                        )
                    } else {
                        if (dealModel.offerPrice != null) {
                            Text(
                                text = "${dealModel.offerPrice}€",
                                color = cw_dark_primary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${dealModel.price ?: 0}€",
                                color = cw_dark_grayText,
                                fontSize = 10.sp,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )
                        } else {
                            Text(
                                text = "${dealModel.price ?: 0}€",
                                color = cw_dark_primary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (dealModel.currentCreatedHour.toInt() == 0)
                            "${dealModel.currentCreatedMinute}m"
                        else if (dealModel.currentCreatedHour < 24) {
                            "${dealModel.currentCreatedHour}h"
                        } else {
                            "${dealModel.currentCreatedDay}T"
                        },
                        fontSize = 10.sp,
                        color = cw_dark_grayText,
                        fontWeight = FontWeight.Medium
                    )
                    dealModel.providerUrl?.let { url ->
                        Icon(painter = painterResource(Res.drawable.external_link),
                            contentDescription = null,
                            tint = cw_dark_primary,
                            modifier = Modifier
                                .size(23.dp)
                                .noRippleClickable {
                                   openUrl(url)
                                }
                        )
                    }
                }
            }
        }
    }
}