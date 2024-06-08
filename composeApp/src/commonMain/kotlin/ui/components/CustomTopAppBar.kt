package ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.chevron_left
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.painterResource
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backButtonAction: (() -> Unit)? = null,
    hasBackground: Boolean = true,
    rightAction: @Composable RowScope.() -> Unit = {},
    textColor: Color = cw_dark_whiteText,
    isDivider: Boolean = true,
) {
    val animatedFloat by animateFloatAsState(
        if (hasBackground) 1f else 0.4f,
        animationSpec = tween(500)
    )
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            cw_dark_background.copy(alpha = if (hasBackground) animatedFloat else 0.4f),
                            cw_dark_background.copy(alpha = if (hasBackground) animatedFloat else 0.4f),
                            cw_dark_background.copy(alpha = if (hasBackground) animatedFloat else 0f)
                        ),
                    )
                )
                .padding(top = 45.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backButtonAction != null) {
                Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterStart) {
                    Box(
                        modifier = Modifier.size(30.dp).noRippleClickable {
                            backButtonAction()
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter =
                            painterResource(Res.drawable.chevron_left),
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.weight(2f))
            }
            Text(
                text = title,
                modifier = Modifier.weight(6f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,

                )
            Row(
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.End,
                content = rightAction
            )
        }
        if (isDivider) {
            CustomDivider()
        }
    }
}
