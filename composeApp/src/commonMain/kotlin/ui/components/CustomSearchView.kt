package ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Search
import compose.icons.tablericons.X
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
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.Transparent)
            .customBorder(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = value,
            onValueChange = onTextChange,
            placeholder = { Text("Search") },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSecondary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = backgroundColor.copy(alpha = animatedFocus),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick(value)
                focusManager.clearFocus(false)
//                focus = false
            }),
            leadingIcon = { Icon(TablerIcons.Search, contentDescription = null) },
            trailingIcon = {
                if (focus)
                    Icon(
                        TablerIcons.X,
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
            modifier = modifier.fillMaxWidth().padding(0.dp)
                .onFocusChanged { focus = it.isFocused }
        )
    }
}