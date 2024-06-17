package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.plus
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_green
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.components.customModiefier.customBorder
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomSelectionView(
    modifier: Modifier = Modifier,
    text: String = "",
    isSelected: Boolean = false,
    icon: DrawableResource? = Res.drawable.plus,
    onAction: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(
                if (isSelected) cw_dark_green else cw_dark_background,
                shape = MaterialTheme.shapes.medium
            )
            .customBorder(shape = MaterialTheme.shapes.medium)
            .noRippleClickable {
                onAction()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text,
                color = cw_dark_whiteText,
                modifier = Modifier.padding(5.dp),
                fontSize = 10.sp
            )
            icon?.let { icon ->
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}