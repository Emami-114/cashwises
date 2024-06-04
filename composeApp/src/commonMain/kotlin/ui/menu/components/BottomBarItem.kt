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
import cashwises.composeapp.generated.resources.Res
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import ui.BottomBarScreen
import ui.components.customModiefier.noRippleClickable

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    tab: BottomBarScreen,
    isSelected: Boolean = false,
    onClick: (BottomBarScreen) -> Unit
) {
    Box(
        modifier = modifier.noRippleClickable {
            onClick(tab)
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
            Icon(
                imageVector = tab.defaultIcon,
                contentDescription = tab.title,
                modifier = Modifier.size(22.dp),
                tint = cw_dark_whiteText
            )
            Spacer(modifier = Modifier.width(5.dp))
            AnimatedVisibility(isSelected) {
                Text(
                    text = tab.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = cw_dark_whiteText
                )
            }
        }
    }
}
