package ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.search
import cashwises.composeapp.generated.resources.x
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import ui.components.customModiefier.customBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
    onFocused: (Boolean) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: @Composable (() -> Unit)? = { Text("Search") },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search,
        autoCorrect = false
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onSearch = {
            onSearchClick(value)
            onFocused(false)
        }),
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    animation: Boolean = true
) {
    var focus: Boolean by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (focus) cw_dark_borderColor.copy(0.6f) else cw_dark_onBackground,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
    )
    val animateDp = animateDpAsState(
        targetValue = if (focus && animation) 50.dp else 40.dp,
        animationSpec = tween(400)
    )
    val mergedTextStyle = textStyle.merge(TextStyle(color = cw_dark_whiteText))
    val colors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.secondary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    BasicTextField(
        value = value,
        modifier = modifier.onFocusChanged {
            focus = it.isFocused
            onFocused(it.isFocused)
        }
            .fillMaxWidth()
            .height(animateDp.value)
            .padding(horizontal = 15.dp)
            .clip(MaterialTheme.shapes.large)
            .background(animatedColor)
            .customBorder()
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = true,
        readOnly = false,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(cw_dark_whiteText),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = 1,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = null,
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.search),
                        contentDescription = null,
                        modifier =  Modifier.size(26.dp)
                    )
                },
                trailingIcon = {
                    if (focus)
                        Icon(
                            painter = painterResource(Res.drawable.x),
                            contentDescription = null,
                            modifier = Modifier.size(26.dp).clickable {
                                if (value.isNotEmpty()) {
                                    onValueChange("")
                                } else {
                                    focus = false
                                    onFocused(false)
                                }
                            })
                },
                shape = shape,
                singleLine = singleLine,
                enabled = true,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(if (focus && animation) 15.dp else 8.dp)
            )
        }
    )
}