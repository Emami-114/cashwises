package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.cloud_upload
import cashwises.composeapp.generated.resources.upload_your_image
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import com.mohamedrejeb.calf.picker.toImageBitmap
import domain.model.ImageModel
import kotlinx.coroutines.launch
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_onBackground
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.customModiefier.customBorder
import utils.resizeImage

@Composable
fun CustomImagePicker(
    modifier: Modifier = Modifier,
    selectedImage: ImageModel?,
    imageSize: Dp = 350.dp,
    height: Dp = 150.dp,
    backGround: Color = cw_dark_onBackground,
    onImageChange: (ImageModel?) -> Unit
) {
    var isImageShowing by remember { mutableStateOf(false) }
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
                            byteArray = resizeImage(
                                imageData = file.readByteArray(context),
                                width = 1024,
                                height = 1024,
                                quality = 100
                            )
                        )
                    )
                }
                isImageShowing = true
            }
        })

    if (selectedImage != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val imageByte = resizeImage(
                imageData = selectedImage.byteArray,
                width = 1024,
                height = 1024,
                quality = 50
            )
            Image(
                bitmap = imageByte.toImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .customBorder()
                    .clip(shape = MaterialTheme.shapes.large)
                    .clickable {
                        pickerLaunch.launch()
                    })
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(title = "Remove") {
                onImageChange(null)
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
                .drawBehind {
                    drawRoundRect(
                        color = cw_dark_borderColor,
                        style = stroke,
                        cornerRadius = CornerRadius(16.dp.toPx())
                    )
                }
                .heightIn(min = 70.dp, max = height)
                .clip(MaterialTheme.shapes.large)
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
                    painter = painterResource(Res.drawable.cloud_upload),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier.height(20.dp))
                Text(
                    stringResource(Res.string.upload_your_image),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
