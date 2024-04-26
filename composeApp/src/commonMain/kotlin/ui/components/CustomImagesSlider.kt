package ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import data.repository.ApiConfig
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_primary
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

    Column(modifier = modifier
        .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            key = { imagesPath?.get(it) ?: 0 },
            pageSpacing = 15.dp,
            modifier = Modifier.weight(9f),
            pageContent = { index ->
                val painter =
                    rememberImagePainter("${ApiConfig.BASE_URL}/images/${imagesPath?.get(index)}")
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        )
        if (imagesPath?.size!! > 1) {
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(imagesPath.size) { index ->
                    Box(
                        modifier = Modifier.size(15.dp)
                            .background(
                                if (pagerState.currentPage == index) cw_dark_primary else cw_dark_grayText,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .noRippleClickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }
        }
    }
}