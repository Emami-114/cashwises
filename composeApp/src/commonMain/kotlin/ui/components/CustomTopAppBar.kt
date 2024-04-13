package ui.components

 import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
 import compose.icons.FeatherIcons
 import compose.icons.feathericons.ChevronLeft
 import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backButtonAction: (() -> Unit)? = null,
    rightAction: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = cw_dark_background,
    textColor: Color = cw_dark_whiteText,
    isDivider: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(backgroundColor)
                .padding(top = 30.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backButtonAction != null) {
                Row(modifier = Modifier.weight(2f)) {
                    Icon(
                        FeatherIcons.ChevronLeft,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.noRippleClickable {
                            backButtonAction()
                        }.size(28.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.weight(2f))
            }
            Text(
                title, modifier = Modifier.weight(6f),
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
