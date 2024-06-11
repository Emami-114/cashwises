package ui.deals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import cashwises.composeapp.generated.resources.heart
import cashwises.composeapp.generated.resources.heart_fill
import coil3.compose.AsyncImage
import data.repository.ApiConfig
import data.repository.UserRepository
import domain.model.DealModel
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.until
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable
import kotlin.math.absoluteValue

@Composable
fun ProductItemRow(
    modifier: Modifier = Modifier,
    dealModel: DealModel,
    onClick: () -> Unit
) {
    val expirationDate: Int? = if (dealModel.expirationDate != null) {
        Clock.System.now()
            .daysUntil(Instant.parse(dealModel.expirationDate), timeZone = TimeZone.UTC)
    } else {
        null
    }
    Row(
        modifier = Modifier.fillMaxWidth().customBorder()
            .alpha(if (expirationDate != null && expirationDate < 0) 0.3f else 1f),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight().clip(MaterialTheme.shapes.large)
        ) {
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
                modifier = Modifier.fillMaxSize().clickable { onClick() }
                    .clip(MaterialTheme.shapes.large).customBorder(),
                model = "${ApiConfig.BASE_URL}/images/${dealModel.thumbnail}",
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                onError = {},
                onLoading = {},
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 90.dp)
                .padding(vertical = 5.dp)
                .padding(end = 5.dp).align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    dealModel.title,
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 3,
                    color = cw_dark_whiteText,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.weight(1f))
                if (UserRepository.INSTANCE.userIsLogged) {
                    Icon(
                        painter = painterResource(
                            if (UserRepository.INSTANCE.isDealMarked(
                                    dealId = dealModel.id ?: ""
                                )
                            ) Res.drawable.heart_fill else Res.drawable.heart
                        ), contentDescription = null,
                        tint = if (UserRepository.INSTANCE.isDealMarked(
                                dealId = dealModel.id ?: ""
                            )
                        ) cw_dark_red else cw_dark_whiteText,
                        modifier = Modifier.size(20.dp).noRippleClickable {
                            runBlocking {
                                UserRepository.INSTANCE.addMarkDealForUser(
                                    dealId = dealModel.id ?: ""
                                )
                            }
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(dealModel.provider ?: "", fontSize = 10.sp, color = cw_dark_whiteText)
//                Text(
//                    text = if (dealModel.currentCreatedHour.toInt() == 0) "${dealModel.currentCreatedMinute}m"
//                    else if (dealModel.currentCreatedHour < 24) {
//                        "${dealModel.currentCreatedHour}h"
//                    } else {
//                        "${dealModel.currentCreatedDay}T"
//                    },
//                    fontSize = 10.sp,
//                    color = cw_dark_grayText,
//                    fontWeight = FontWeight.Medium
//                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (dealModel.isFree == true) {
                        Text(text = "Free", color = cw_dark_primary, fontSize = 17.sp)
                    } else {
                        if (dealModel.offerPrice != null) {
                            Text(
                                text = "${dealModel.offerPrice}€",
                                color = cw_dark_primary,
                                fontSize = 17.sp
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
                                fontSize = 17.sp
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.external_link),
                        contentDescription = null,
                        tint = cw_dark_primary,
                        modifier = Modifier.size(30.dp).noRippleClickable {

                        })
                }
            }
        }
    }
}