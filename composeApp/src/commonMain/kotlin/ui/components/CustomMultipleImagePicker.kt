package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
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
import compose.icons.FeatherIcons
import compose.icons.feathericons.UploadCloud
import domain.model.ImageModel
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_onBackground
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomMultipleImagePicker(
    modifier: Modifier = Modifier,
    backGround: Color = cw_dark_onBackground,
    selectedImage: List<ImageModel> = listOf(),
    onImageChange: (List<ImageModel>?) -> Unit
) {
    val selectedItems =
        remember { mutableStateListOf<ImageModel>().apply { addAll(selectedImage) } }
    val scopeCoroutine = rememberCoroutineScope()
    val context = LocalPlatformContext.current
    val pickerLaunch = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { kmpFiles ->
            scopeCoroutine.launch {
                kmpFiles.forEach { image ->
                    selectedItems.add(
                        ImageModel(
                            name = image.getName(context) ?: "",
                            path = image.getPath(context) ?: "",
                            byteArray = image.readByteArray(context)
                        )
                    )
                }
                onImageChange(selectedItems.toList())
            }
        })
    if (selectedItems.isEmpty().not()) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.clickable {
                    pickerLaunch.launch()
                }) {
                    selectedItems.forEach { imageModel ->
                        val painter = rememberImagePainter(imageModel.path)
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(140.dp)
                                .customBorder()
                                .clip(shape = MaterialTheme.shapes.large)
                        )
                    }
                }
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Top)
                        .size(40.dp)
                        .zIndex(1f)
                        .noRippleClickable {
                            onImageChange(null)
                            selectedItems.clear()
                        }
                )
            }

        }
    } else {
        val stroke = Stroke(
            width = 5f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 70.dp, max = 150.dp)
                .drawBehind {
                    drawRoundRect(
                        color = cw_dark_borderColor,
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
                    FeatherIcons.UploadCloud,
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