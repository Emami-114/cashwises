package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.bad_request_error
import org.company.app.theme.cw_dark_blackText
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPopUp(
    present: Boolean = false,
    onDismissDisable: Boolean = false,
    message: String = "",
    cancelTitle: String = "Ok",
    cancelAction: () -> Unit = {},
    acceptAction: (@Composable RowScope.() -> Unit)? = null
) {
    var dialogPresent by remember { mutableStateOf(present) }
    AnimatedVisibility(visible = dialogPresent) {
        BasicAlertDialog(
            onDismissRequest = { if (!onDismissDisable) dialogPresent = false },
            modifier = Modifier.background(cw_dark_whiteText, shape = MaterialTheme.shapes.large),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(message, color = cw_dark_blackText, fontSize = 14.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CustomButton(
                        modifier = Modifier
                            .height(35.dp)
                            .weight(5f),
                        title = cancelTitle,
                    ) {
                        dialogPresent = false
                        cancelAction()
                    }
                    if (acceptAction != null) {
                        Row(content = acceptAction, modifier = Modifier.height(35.dp).weight(5f))
                    }
                }

            }

        }
    }

}