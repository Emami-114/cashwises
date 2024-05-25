package ui.components

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowBackUp
import compose.icons.tablericons.ChevronLeft
import compose.icons.tablericons.Plus
import org.company.app.theme.cw_dark_background
import org.company.app.theme.cw_dark_blackText
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_whiteText
import ui.components.customModiefier.noRippleClickable

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    backButtonAction: (() -> Unit)? = null,
    hasBackButtonBackground: Boolean = false,
    rightAction: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = cw_dark_background,
    textColor: Color = if (hasBackButtonBackground) cw_dark_blackText else cw_dark_whiteText,
    isDivider: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(backgroundColor)
                .padding(top = 30.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backButtonAction != null) {
                Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterStart) {
                    Box(
                        modifier = Modifier.size(30.dp)
                            .background(
                                if (hasBackButtonBackground)
                                    cw_dark_background else Color.Transparent,
                                shape = MaterialTheme.shapes.extraLarge
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            TablerIcons.ChevronLeft,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.noRippleClickable {
                                backButtonAction()
                            }.size(25.dp)
                        )
                    }
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
