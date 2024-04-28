package ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.company.app.theme.cw_dark_borderColor
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_onBackground
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.customBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.large,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    errorText: String? = null,
    contentPadding: PaddingValues = TextFieldDefaults.contentPaddingWithLabel()
) {
    var focus: Boolean by remember { mutableStateOf(false) }
    val colorAnimated by animateColorAsState(
        targetValue = if (focus) cw_dark_grayText.copy(alpha = 0.6f) else cw_dark_borderColor,
        animationSpec = tween(200, easing = EaseInOut)
    )
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = cw_dark_onBackground,
        unfocusedContainerColor = cw_dark_onBackground,
        focusedTextColor = cw_dark_whiteText,
        unfocusedTextColor = cw_dark_whiteText,
        focusedTrailingIconColor = cw_dark_grayText,
        unfocusedTrailingIconColor = cw_dark_grayText,
        focusedLeadingIconColor = cw_dark_grayText,
        unfocusedLeadingIconColor = cw_dark_grayText,
        cursorColor = cw_dark_whiteText,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedPlaceholderColor = cw_dark_grayText,
        unfocusedPlaceholderColor = cw_dark_grayText
    )
    val textColor = textStyle.color.takeOrElse {
        colors.focusedTextColor
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    Column {
        BasicTextField(
            value = value,
            modifier = modifier.fillMaxWidth()
                .clip(shape)
                .background(cw_dark_onBackground, shape)
                .customBorder(color = if (errorText != null) cw_dark_red else colorAnimated)
                .onFocusChanged { focus = it.isFocused }
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                ),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = { Text(placeholder) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    shape = shape,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = contentPadding
                )
            }
        )
        if (errorText != null) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                errorText,
                style = MaterialTheme.typography.labelSmall,
                color = cw_dark_red,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }
}