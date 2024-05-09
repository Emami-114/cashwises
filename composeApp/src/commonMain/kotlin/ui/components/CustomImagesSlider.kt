package ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.repository.ApiConfig
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomImagesSlider(
    modifier: Modifier = Modifier,
    thumbnail: String? = null,
    paths: List<String>? = null
) {
    val imagesPath = if (paths.isNullOrEmpty().not()) {
        paths?.plus(thumbnail!!)
    } else {
        listOf(thumbnail!!)
    }
    val pagerState = rememberPagerState(pageCount = { imagesPath?.count() ?: 0 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
            .heightIn(max = 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(9f), contentAlignment = Alignment.Center) {
            HorizontalPager(
                state = pagerState,
                key = { imagesPath?.get(it) ?: 0 },
                pageSpacing = 15.dp,
                modifier = Modifier,
                pageContent = { index ->
                    val painter =
                        rememberImagePainter("${ApiConfig.BASE_URL}/images/${imagesPath?.get(index)}")
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 0.dp,
                                    topStart = 0.dp,
                                    bottomEnd = 30.dp,
                                    bottomStart = 30.dp
                                )
                            )
                    )
                }
            )

            if (imagesPath?.size!! > 1) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(horizontal = 30.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            color = cw_dark_whiteText.copy(0.3f),
                            shape = MaterialTheme.shapes.large
                        )
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    imagesPath.forEachIndexed { index, path ->
                        val painter =
                            rememberImagePainter("${ApiConfig.BASE_URL}/images/${path}")
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .height(45.dp)
                                .width(65.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .customBorder(
                                    color = if (pagerState.currentPage == index) cw_dark_primary else Color.Transparent,
                                    width = 2.dp,
                                    shape = MaterialTheme.shapes.medium
                                ).noRippleClickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
//        if (imagesPath?.size!! > 1) {
//            Row(
//                modifier = Modifier.fillMaxWidth().weight(1f),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                repeat(imagesPath.size) { index ->
//                    Box(
//                        modifier = Modifier.size(10.dp)
//                            .background(
//                                if (pagerState.currentPage == index) cw_dark_primary else cw_dark_grayText,
//                                shape = MaterialTheme.shapes.extraLarge
//                            )
//                            .noRippleClickable {
//                                scope.launch {
//                                    pagerState.animateScrollToPage(index)
//                                }
//                            }
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                }
//            }
//        }
    }
}