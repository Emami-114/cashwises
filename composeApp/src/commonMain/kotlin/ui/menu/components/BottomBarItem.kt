package ui.menu.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.customModiefier.noRippleClickable

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    title: StringResource?,
    icon: DrawableResource?,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.height(50.dp)
                .background(
                    if (isSelected) cw_dark_primary else Color.Transparent,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let { icon ->
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = cw_dark_whiteText
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            AnimatedVisibility(isSelected) {
                title?.let { title ->
                    Text(
                        text = stringResource(title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = cw_dark_whiteText
                    )
                }
            }
        }
    }
}
