package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import org.company.app.theme.md_theme_dark_green
import org.company.app.theme.md_theme_dark_primary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalRichTextApi::class)
@Composable
fun CustomRichTextEditor(
    state: RichTextState = rememberRichTextState(),
    modifier: Modifier = Modifier
) {
    val titleSize = MaterialTheme.typography.titleLarge.fontSize
    val subTitleSize = MaterialTheme.typography.displaySmall.fontSize
    val textRange by mutableStateOf(TextRange.Zero)

    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 20.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EditorControls(
                modifier = Modifier,
                state = state,
                onBoldClick = {
                    state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                },
                onItalicClick = {
                    state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                },
                onUnderlineClick = {
                    state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                },
                onTitleClick = {
                    state.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                },
                onSubtitleClick = {
                    state.toggleSpanStyle(SpanStyle(fontSize = subTitleSize))
                },
                onTextColorClick = { color ->
                    state.toggleSpanStyle(SpanStyle(color = color))
                },
                onStartAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                },
                onEndAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                },
                onCenterAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                },
                onFormatNumberClick = {

                },
                onFormatBulletedClick = {
                    if (state.isUnorderedList) {
                        state.removeUnorderedList()
                    } else {
                        state.addUnorderedList()
                    }
                },
                onExportClick = {
                    println("Editor ** ${state.toHtml()}")
                }
            )
            RichTextEditor(
                modifier = modifier
                    .fillMaxWidth(),
                state = state,
                shape = MaterialTheme.shapes.large,
                colors = RichTextEditorDefaults.richTextEditorColors(
                    textColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    state: RichTextState,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onTextColorClick: (color: Color) -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onFormatNumberClick: () -> Unit,
    onFormatBulletedClick: () -> Unit,
    onExportClick: () -> Unit,
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorRedSelected by rememberSaveable { mutableStateOf(false) }
    var textColorGreenSelected by rememberSaveable { mutableStateOf(false) }
    var textColorPrimarySelected by rememberSaveable { mutableStateOf(false) }
    var linkSelected by rememberSaveable { mutableStateOf(false) }
    var formatListNumber by rememberSaveable { mutableStateOf(false) }
    var formatListBulleted by rememberSaveable { mutableStateOf(false) }
    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }

    val showLinkDialog = remember { mutableStateOf(false) }

    AnimatedVisibility(visible = showLinkDialog.value) {
        LinkDialog(showLinkDialog = showLinkDialog, onSubmit = { text, url ->
            state.addLink(text = text, url = url)
            showLinkDialog.value = false
            linkSelected = false
        }, onDismiss = {
            showLinkDialog.value = false
            linkSelected = false
        })
    }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick,
            icon = Icons.Default.FormatBold
        )
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick,
            icon = Icons.Default.FormatItalic
        )
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick,
            icon = Icons.Default.FormatUnderlined
        )
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick,
            icon = Icons.Default.Title
        )
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick,
            icon = Icons.Default.FormatSize
        )
        ControlWrapper(
            selected = textColorRedSelected,
            selectedColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
            unselectedColor = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
            onChangeClick = {
                textColorRedSelected = it
                textColorPrimarySelected = false
                textColorGreenSelected = false
            },
            onClick = { onTextColorClick(Color.Red) },
            icon = Icons.Default.FormatColorFill
        )
        ControlWrapper(
            selected = textColorGreenSelected,
            selectedColor = md_theme_dark_green.copy(alpha = 1f),
            unselectedColor = md_theme_dark_green.copy(alpha = 0.7f),
            onChangeClick = {
                textColorGreenSelected = it
                textColorPrimarySelected = false
                textColorRedSelected = false
            },
            onClick = { onTextColorClick(md_theme_dark_green) },
            icon = Icons.Default.FormatColorFill
        )
        ControlWrapper(
            selected = textColorPrimarySelected,
            selectedColor = md_theme_dark_primary.copy(alpha = 1f),
            unselectedColor = md_theme_dark_primary.copy(alpha = 0.7f),
            onChangeClick = {
                textColorPrimarySelected = it
                textColorGreenSelected = false
                textColorRedSelected = false
            },
            onClick = { onTextColorClick(md_theme_dark_primary) },
            icon = Icons.Default.FormatColorFill
        )
        ControlWrapper(
            selected = linkSelected,
            onChangeClick = { linkSelected = it },
            onClick = { showLinkDialog.value = true },
            icon = Icons.Default.AddLink
        )
        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick,
            icon = Icons.Default.FormatAlignLeft
        )
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick,
            icon = Icons.Default.FormatAlignCenter
        )
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick,
            icon = Icons.Default.FormatAlignRight
        )
        ControlWrapper(
            selected = formatListNumber,
            onChangeClick = { formatListNumber = it },
            onClick = {
                if (!state.isOrderedList) {
                    state.addOrderedList()
                    state.removeUnorderedList()
                    formatListBulleted = false
                } else {
                    state.removeOrderedList()
                    state.removeUnorderedList()
                }
//                onFormatNumberClick()
            },
            icon = Icons.Default.FormatListNumbered
        )
        ControlWrapper(
            selected = formatListBulleted,
            onChangeClick = { formatListBulleted = it },
            onClick = {
                if (!state.isUnorderedList) {
                    state.addUnorderedList()
                    state.removeOrderedList()
                    formatListNumber = false
                } else {
                    state.removeUnorderedList()
                }
            },
            icon = Icons.Default.FormatListBulleted
        )

//        ControlWrapper(
//            selected = true,
//            selectedColor = MaterialTheme.colorScheme.tertiary,
//            onChangeClick = { },
//            onClick = onExportClick,
//            icon = Icons.Default.Save
//        )
    }
}


@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSecondary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.secondary else selectedColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkDialog(
    showLinkDialog: MutableState<Boolean>,
    onSubmit: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    if (showLinkDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { showLinkDialog.value = false },
        ) {
            Column(
                modifier =
                Modifier.clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = "Text"
                )
                CustomTextField(
                    value = url,
                    onValueChange = { url = it },
                    placeholder = "Url"
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomButton(
                        modifier = Modifier.weight(1f),
                        title = "Cancel",
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ) {
                        text = ""
                        url = ""
                        onDismiss()
                    }
                    CustomButton(modifier = Modifier.weight(1f), title = "Add") {
                        onSubmit(text, url)
                    }

                }
            }
        }
    }
}