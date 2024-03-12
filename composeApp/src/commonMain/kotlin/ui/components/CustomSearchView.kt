package ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ui.components.customModiefier.customBorder

@Composable
fun CustomSearchView(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onFocused: (Boolean) -> Unit
) {
    var focus: Boolean by remember { mutableStateOf(false) }
    val animatedFocus by animateFloatAsState(
        targetValue = if (focus) 0.15f else 0.0f,
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
//            .padding(horizontal = 5.dp)

    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text("Search")
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.secondary,
                placeholderColor = MaterialTheme.colorScheme.onSecondary,
                leadingIconColor = MaterialTheme.colorScheme.onSecondary,
                trailingIconColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                backgroundColor =
                backgroundColor.copy(
                    alpha = animatedFocus
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,

                ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClick(text)
                focusManager.clearFocus(false)
//                focus = false
            }),
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            trailingIcon = {
                if (focus)
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
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