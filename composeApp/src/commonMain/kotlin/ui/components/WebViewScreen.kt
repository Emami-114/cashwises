package ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import utils.WebPageViewer

@Composable
fun WebViewScreen(
    url: String,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            CustomTopAppBar(title = "", backButtonAction = {
                onNavigateBack()
            })
//            if (state.isLoading) {
//                LinearProgressIndicator(
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
            WebPageViewer(url)
        }
    }
}
