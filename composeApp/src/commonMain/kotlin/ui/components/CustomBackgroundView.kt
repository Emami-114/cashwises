package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomBackgroundView() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .blur(50.dp)
    ) {
        Box(
            modifier =
            Modifier.fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.03f),
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.03f),
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f),
                        )
                    ),
                )
        )
//
//        Box(
//            modifier =
//            Modifier.size(50.dp).align(Alignment.TopEnd)
//                .background(Color.White.copy(alpha = 0.23f))
//        )
//
//        Box(
//            modifier =
//            Modifier.size(90.dp).align(Alignment.BottomEnd)
//                .background(Color.White.copy(alpha = 0.2f))
//        )
//
//        Box(
//            modifier =
//            Modifier.size(70.dp).align(Alignment.TopStart)
//                .background(Color.White.copy(alpha = 0.2f))
//        )
//
//        Box(
//            modifier =
//            Modifier.size(90.dp).align(Alignment.BottomStart)
//                .background(Color.White.copy(alpha = 0.2f))
//        )
    }
}