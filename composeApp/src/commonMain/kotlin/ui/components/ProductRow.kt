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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.seiko.imageloader.rememberImagePainter
import data.repository.ApiConfig
import domain.model.DealModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductRow(dealModel: DealModel, onClick: () -> Unit) {
    val roundedCornerShape = RoundedCornerShape(15.dp)
    Box(
        modifier = Modifier.fillMaxSize()
            .clip(roundedCornerShape)
            .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy((-10).dp)
        ) {
            val painter =
                rememberImagePainter(url = "${ApiConfig.BASE_URL}/images/${dealModel.thumbnail}")
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth().padding(all = 10.dp).clip(roundedCornerShape)
            )
            Box(
                modifier = Modifier.fillMaxWidth().clip(roundedCornerShape)
                    .background(Color.Gray.copy(alpha = 0.5f)).padding(5.dp)
            ) {
                Column(modifier = Modifier.zIndex(1f)) {
                    Text(
                        dealModel.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            dealModel.provider ?: "Test",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text("1T", fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${dealModel.price}€", fontSize = 18.sp)

                        Icon(
                            imageVector = Icons.Default.OpenInNew,
                            contentDescription = null,
                            tint = Color.Green.copy(alpha = 0.7f),
                            modifier = Modifier.noRippleClickable {

                            }
                        )
                    }
                }
            }

        }

    }
}

