package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.seiko.imageloader.rememberImagePainter
import data.repository.ApiConfig
import domain.model.DealModel
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductRow(dealModel: DealModel, onClick: () -> Unit) {
    val roundedCornerShape = RoundedCornerShape(15.dp)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.large)) {
                if (dealModel.offerPrice != null) {
                    val offerPercent = ((dealModel.offerPrice / dealModel.price!!) * 100).toInt()
                    Box(
                        modifier = Modifier.padding(start = 18.dp)
                            .height(30.dp)
                            .background(cw_dark_primary)
                            .padding(2.dp)
                            .zIndex(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${offerPercent}%",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    if (offerPercent > 25) {
                        Icon(
                            Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 10.dp)
                                .zIndex(1f),
                            tint = cw_dark_primary
                        )
                    }
                }
                val painter =
                    rememberImagePainter("${ApiConfig.BASE_URL}/images/${dealModel.thumbnail}")
                Image(
                    painter = painter,
                    contentDescription = dealModel.thumbnail,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onClick() }
                        .clip(MaterialTheme.shapes.large)
                )
            }
            Text(
                dealModel.title,
                fontSize = 12.sp,
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
                    val currentDate =
                        if (dealModel.currentTime > 0) "${dealModel.currentTime}T" else "Heute"
                    Text(
                        text = currentDate,
                        fontSize = 10.sp,
                        color = cw_dark_grayText,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = null,
                        tint = cw_dark_primary,
                        modifier = Modifier.size(25.dp).noRippleClickable {

                        }
                    )
                }
            }
        }
    }
}
