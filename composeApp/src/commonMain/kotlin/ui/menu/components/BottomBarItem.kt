package ui.menu.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.company.app.theme.cw_dark_primary
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.noRippleClickable
import ui.menu.BottomBarScreens

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    tab: BottomBarScreens,
    isSelected: Boolean = false,
    onClick: (BottomBarScreens) -> Unit
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
                imageVector = tab.icon,
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
