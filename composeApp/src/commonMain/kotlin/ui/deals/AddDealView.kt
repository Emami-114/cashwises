package ui.deals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
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
import org.koin.compose.koinInject
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomTextField

@Composable
fun AddDealView() {
    val viewModel: DealsViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val scope = this
        val maxWith = scope.maxWidth
        val maxHeight = scope.maxHeight
        CustomBackgroundView()
        val scopeCoroutine = rememberCoroutineScope()
        val context = LocalPlatformContext.current
        val pickerLaunch = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { kmpFiles ->
                for (i in kmpFiles) {
                    scopeCoroutine.launch {
                        i?.let { file ->
                            viewModel.doChangeImage(
                                ImageModel(
                                    name = file.getName(context).toString(),
                                    path = file.getPath(context).toString(),
                                    byteArray = file.readByteArray(context)
                                )
                            )
                        }
                    }
                }
            })
        val pickerLaunch2 = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
            selectionMode = FilePickerSelectionMode.Multiple,
            onResult = { kmpFiles ->
                for (i in kmpFiles) {
                    scopeCoroutine.launch {
                        i?.let { file ->
                            viewModel.doChangeImages(
                                ImageModel(
                                    name = file.getName(context).toString(),
                                    path = file.getPath(context).toString(),
                                    byteArray = file.readByteArray(context)
                                )
                            )
                        }
                    }
                }
            })

        val scrollState = rememberScrollState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (maxWith > 900.dp) 2 else 1),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(horizontal = if (maxWith > 900.dp) 50.dp else 10.dp)
        ) {
            item() {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    val painter =
                        rememberImagePainter(uiState.thumbnailByte?.path ?: "")
                    if (uiState.thumbnailByte != null) {
                        Image(
                            painter, contentDescription = null, contentScale = ContentScale.Crop,
                            modifier = Modifier.height(if (maxWith > 800.dp) 500.dp else 300.dp)
                                .width(if (maxWith > 800.dp) 500.dp else 300.dp)
                        )
                    }
                    Button(onClick = { pickerLaunch.launch() }) {
                        Text("Thumbnail")
                    }
                }
            }
            item {
                val painter =
                    rememberImagePainter(uiState.imagesByte?.first()?.path ?: "")
                if (uiState.imagesByte?.first() != null) {
                    Image(
                        painter, contentDescription = null, contentScale = ContentScale.Crop,
                        modifier = Modifier.height(if (maxWith > 800.dp) 500.dp else 300.dp)
                            .width(if (maxWith > 800.dp) 500.dp else 300.dp)
                    )
                }
                Button(onClick = { pickerLaunch2.launch() }) {
                    Text("Images")
                }
            }

            item {
                CustomTextField(value = uiState.title,
                    onValueChange = { viewModel.onEvent(DealEvent.OnTitleChange(it)) },
                    placeholder = {
                        Text(
                            "Title", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
            item {
                CustomTextField(value = uiState.description,
                    onValueChange = { viewModel.onEvent(DealEvent.OnDescriptionChange(it)) },
                    placeholder = {
                        Text(
                            "Description", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
            item {
                CustomTextField(value = uiState.category ?: "",
                    onValueChange = { viewModel.onEvent(DealEvent.OnCategoryChange(it)) },
                    placeholder = {
                        Text(
                            "Category", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Switch(checked = uiState.isFree ?: false,
                        onCheckedChange = { viewModel.onEvent(DealEvent.OnIsFreeChange(it)) })
                    Switch(checked = uiState.published ?: false,
                        onCheckedChange = { viewModel.onEvent(DealEvent.OnPublishedChange(it)) })
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    CustomTextField(
                        value = uiState.price ?: "",
                        onValueChange = { viewModel.onEvent(DealEvent.OnPriceChange(it)) },
                        placeholder = {
                            Text(
                                "Price", color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        enabled = uiState.isFree != true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.alpha(if (uiState.isFree == true) 0.4f else 1f)
                    )
                }
            }
            item {
                CustomTextField(
                    value = uiState.offerPrice ?: "",
                    onValueChange = { viewModel.onEvent(DealEvent.OnOfferPriceChange(it)) },
                    placeholder = {
                        Text(
                            "Offer price", color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number
                    ), enabled = uiState.isFree != true,
                    modifier = Modifier.alpha(if (uiState.isFree == true) 0.4f else 1f)

                )
            }
            item {
                CustomTextField(value = uiState.provider ?: "",
                    onValueChange = { viewModel.onEvent(DealEvent.OnProviderChange(it)) },
                    placeholder = {
                        Text(
                            "Provider", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
            item {
                CustomTextField(value = uiState.providerUrl ?: "",
                    onValueChange = { viewModel.onEvent(DealEvent.OnProviderUrlChange(it)) },
                    placeholder = {
                        Text(
                            "provider url", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }
            item {
                CustomTextField(value = uiState.videoUrl ?: "",
                    onValueChange = { viewModel.onEvent(DealEvent.OnVideoUrlChange(it)) },
                    placeholder = {
                        Text(
                            "Video url", color = MaterialTheme.colorScheme.secondary
                        )
                    })
            }

            item {
                CustomButton(onClick = {
                    viewModel.onEvent(DealEvent.OnAction)
                }, title = "Create")
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    }

}


