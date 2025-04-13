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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.repository.ApiConfig
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomImagesSlider(
    modifier: Modifier = Modifier,
    paths: List<String>? = null
) {
    val pagerState = rememberPagerState(pageCount = { paths?.count() ?: 0 })
    val scope = rememberCoroutineScope()
    val imagePaths = paths ?: listOf()
    Column(
        modifier = modifier.fillMaxWidth()
            .heightIn(max = 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(9f), contentAlignment = Alignment.Center) {
            HorizontalPager(
                state = pagerState,
                key = { imagePaths[it] },
                pageSpacing = 15.dp,
                modifier = Modifier,
                pageContent = { index ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 0.dp,
                                    topStart = 0.dp,
                                    bottomEnd = 30.dp,
                                    bottomStart = 30.dp
                                )
                            ),
                        model = "${ApiConfig.IMAGE_URL}/${imagePaths[index]}",
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        onError = {},
                        onLoading = {},
                    )
                }
            )

            if (imagePaths.size > 1) {
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
                    imagePaths.forEachIndexed { index, path ->
                        AsyncImage(
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
                                },
                            model = "${ApiConfig.IMAGE_URL}/${path}",
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            onError = {},
                            onLoading = {},
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}