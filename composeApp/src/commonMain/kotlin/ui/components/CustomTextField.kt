package ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import ui.components.customModiefier.customBorder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    text: String = "",
    onchangeText: (String) -> Unit,
    placeholder: String = "",
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var text by remember { mutableStateOf("") }
    var focus: Boolean by remember { mutableStateOf(false) }
    val animatedFocus by animateFloatAsState(
        targetValue = if (focus) 0.20f else 0.0f,
        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
    )
    TextField(value = text, onValueChange = { text = it },
        placeholder = {
            Text(placeholder)
        }, colors = textFieldColors(
            textColor = MaterialTheme.colorScheme.secondary,
            placeholderColor = MaterialTheme.colorScheme.onSecondary,
            leadingIconColor = MaterialTheme.colorScheme.onSecondary,
            trailingIconColor = MaterialTheme.colorScheme.onSecondary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            backgroundColor = MaterialTheme.colorScheme.onSecondary.copy(
                alpha = animatedFocus
            ),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            ), leadingIcon = leadingIcon, trailingIcon = trailingIcon, singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
            .clip(MaterialTheme.shapes.large).background(Color.Transparent).customBorder()
            .height(60.dp).onFocusChanged { focus = it.isFocused })
}

@Composable
fun DuckieTextField2(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        textStyle = LocalTextStyle.current.copy(fontSize = 25.sp),
        modifier = Modifier.fillMaxWidth().customBorder().padding(20.dp),
        cursorBrush = SolidColor(Color.White),

        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color.Yellow,
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx(),
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx(),
                        ),
                        strokeWidth = 1.dp.toPx(),
                    )
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFFA8A8A8),
                    )
                    innerTextField()
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        },
    )
}