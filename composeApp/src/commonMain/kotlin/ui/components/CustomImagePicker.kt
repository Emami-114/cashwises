package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.seiko.imageloader.rememberImagePainter
import domain.model.ImageModel
import kotlinx.coroutines.launch
import org.company.app.theme.md_theme_dark_surface
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomImagePicker(
    modifier: Modifier = Modifier,
    selectedImage: ImageModel?,
    imageSize: Dp = 350.dp,
    height: Dp = 150.dp,
    backGround: Color = MaterialTheme.colorScheme.onPrimary,
    onImageChange: (ImageModel?) -> Unit
) {
    val scopeCoroutine = rememberCoroutineScope()
    val context = LocalPlatformContext.current
    val pickerLaunch = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { kmpFiles ->
            scopeCoroutine.launch {
                kmpFiles.firstOrNull()?.let { file ->
                    onImageChange(
                        ImageModel(
                            name = file.getName(context) ?: "",
                            path = file.getPath(context) ?: "",
                            byteArray = file.readByteArray(context)
                        )
                    )
                }
            }
        })

    if (selectedImage != null) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(imageSize),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp)
                        .zIndex(1f)
                        .noRippleClickable {
                            onImageChange(null)
                        }
                )
                val painter = rememberImagePainter(selectedImage.path)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .customBorder()
                        .clip(shape = MaterialTheme.shapes.large)
                        .clickable {
                            pickerLaunch.launch()
                        }
                )
            }
        }
    } else {
        val stroke = Stroke(
            width = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 70.dp, max = height)
                .drawBehind {
                    drawRoundRect(
                        color = md_theme_dark_surface,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }.clip(MaterialTheme.shapes.large)
                .clickable {
                    pickerLaunch.launch()
                },
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                hoveredElevation = 6.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backGround),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.CloudUpload,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier.height(20.dp))
                Text(
                    "Upload your Image",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}