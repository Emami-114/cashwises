package ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Search
import compose.icons.feathericons.X
import org.company.app.theme.cw_dark_onBackground
import ui.components.customModiefier.customBorder

@Composable
fun CustomSearchView(
    modifier: Modifier = Modifier,
    value: String,
    backgroundColor: Color = cw_dark_onBackground,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onFocused: (Boolean) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    textStyle: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    ) {
    var focus: Boolean by remember { mutableStateOf(false) }
    val animatedFocus by animateFloatAsState(
        targetValue = if (focus) 0.5f else 1f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )
    val focusManager = LocalFocusManager.current
//    Box(
//        modifier = modifier.fillMaxWidth()
//            .padding(horizontal = 15.dp)
//            .clip(MaterialTheme.shapes.large)
//            .background(Color.Transparent)
//            .customBorder(),
//        contentAlignment = Alignment.Center
//    ) {
//        TextField(
//            value = value,
//            onValueChange = onTextChange,
//            placeholder = { Text("Search") },
//            colors = TextFieldDefaults.textFieldColors(
//                textColor = MaterialTheme.colorScheme.secondary,
//                placeholderColor = MaterialTheme.colorScheme.onSecondary,
//                leadingIconColor = MaterialTheme.colorScheme.onSecondary,
//                trailingIconColor = MaterialTheme.colorScheme.onSecondary,
//                cursorColor = MaterialTheme.colorScheme.secondary,
//                backgroundColor = backgroundColor.copy(alpha = animatedFocus),
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(onSearch = {
//                onSearchClick(value)
//                focusManager.clearFocus(false)
////                focus = false
//            }),
//            leadingIcon = { Icon(FeatherIcons.Search, contentDescription = null) },
//            trailingIcon = {
//                if (focus)
//                    Icon(
//                        FeatherIcons.X,
//                        contentDescription = null,
//                        modifier = Modifier.clickable {
//                            if (value.isNotEmpty()) {
//                                onTextChange("")
//                            } else {
//                                focus = false
//                                focusManager.clearFocus(false)
//                            }
//                        })
//            },
//            modifier = modifier.fillMaxWidth().padding(0.dp)
//                .onFocusChanged { focus = it.isFocused }
//        )
    // If color is not provided via the text style, use content color as a default

    val colors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.secondary,
        placeholderColor = MaterialTheme.colorScheme.onSecondary,
        leadingIconColor = MaterialTheme.colorScheme.onSecondary,
        trailingIconColor = MaterialTheme.colorScheme.onSecondary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        backgroundColor = backgroundColor.copy(alpha = animatedFocus),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(true).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    (BasicTextField(
        value = value,
        modifier = modifier.fillMaxWidth()
            .height(48.dp)
            .customBorder()
            .background(colors.backgroundColor(true).value, MaterialTheme.shapes.large)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            )
            .onFocusChanged { focus = it.isFocused },
        onValueChange = onTextChange,
        enabled = true,
        readOnly = false,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(false).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = { Text("Search") },
                label = null,
                leadingIcon = { Icon(FeatherIcons.Search, contentDescription = null) },
                trailingIcon = {
                    if (focus)
                        Icon(
                            FeatherIcons.X,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                if (value.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    focus = false
                                    focusManager.clearFocus(false)
                                }
                            })
                },
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(0.dp)

            )
        }
    ))
//    }
}