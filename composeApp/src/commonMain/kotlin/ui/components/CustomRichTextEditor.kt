package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import cashwises.composeapp.generated.resources.Res
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import compose.icons.TablerIcons
import compose.icons.tablericons.AlignCenter
import compose.icons.tablericons.AlignLeft
import compose.icons.tablericons.AlignRight
import compose.icons.tablericons.Bold
import compose.icons.tablericons.Italic
import compose.icons.tablericons.LetterT
import compose.icons.tablericons.Link
import compose.icons.tablericons.List
import compose.icons.tablericons.Underline
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_blackText
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_onPrimary
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalRichTextApi::class)
@Composable
fun CustomRichTextEditor(
    state: RichTextState = rememberRichTextState(),
    modifier: Modifier = Modifier
) {
    val titleSize = MaterialTheme.typography.titleLarge.fontSize
    val subTitleSize = MaterialTheme.typography.displaySmall.fontSize
    val textRange by mutableStateOf(TextRange.Zero)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 20.dp),
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
                textColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
        )
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
            icon = TablerIcons.Bold
        )
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick,
            icon = TablerIcons.Italic
        )
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick,
            icon = TablerIcons.Underline
        )
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick,
            icon = TablerIcons.LetterT
        )
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick,
            icon = TablerIcons.LetterT
        )
        CustomColorPicker { color ->
            onTextColorClick(color)
        }

        ControlWrapper(
            selected = linkSelected,
            onChangeClick = { linkSelected = it },
            onClick = { showLinkDialog.value = true },
            icon = TablerIcons.Link
        )
        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick,
            icon = TablerIcons.AlignLeft
        )
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick,
            icon = TablerIcons.AlignCenter
        )
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick,
            icon = TablerIcons.AlignRight
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
            icon = TablerIcons.List
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
            icon = TablerIcons.List
        )
    }
}


@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = cw_dark_onPrimary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 8.dp))
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
                color = cw_dark_borderColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = cw_dark_whiteText
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomColorPicker(selectedColor: (Color) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val colors = listOf(
        cw_dark_background, cw_dark_primary, cw_dark_onPrimary, cw_dark_whiteText,
        cw_dark_grayText, cw_dark_blackText, cw_dark_green, cw_dark_red
    )
    var selected by remember { mutableStateOf(cw_dark_whiteText) }
    Box(modifier = Modifier.clip(MaterialTheme.shapes.medium)
        .background(selected)
        .clickable { expanded = !expanded }
        .size(40.dp)
        .customBorder(shape = MaterialTheme.shapes.medium)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .size(150.dp)
                .background(cw_dark_onBackground)
                .padding(5.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                colors.forEach { color: Color ->
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .size(30.dp)
                            .background(color)
                            .clickable {
                                selectedColor(color)
                                selected = color
                                expanded = false
                            }
                    )
                }
            }

        }
    }
}