package ui.account.auth.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.auth_verification_description
import cashwises.composeapp.generated.resources.auth_verification_not_receive
import cashwises.composeapp.generated.resources.auth_verification_title
import cashwises.composeapp.generated.resources.button_send
import org.company.app.theme.cw_dark_grayText
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomTextField

@Composable
fun VerificationView(errorMessage: String? = null, onAction: (code: String) -> Unit) {
    var verificationCode by remember { mutableStateOf(List(4) { "" }) }
    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        if (errorMessage != null) {
            CustomPopUp(present = true, onDismissDisable = true, message = errorMessage)
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(Res.string.auth_verification_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = cw_dark_whiteText
                )
                Text(
                    stringResource(Res.string.auth_verification_description),
                    color = cw_dark_grayText,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (index in verificationCode.indices) {
                        CustomTextField(
                            modifier = Modifier.size(70.dp),
                            value = verificationCode[index],
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                    verificationCode = verificationCode.toMutableList().also {
                                        it[index] = newValue
                                    }
                                } else {
                                    verificationCode = verificationCode.toMutableList().also {
                                        it[index] = ""
                                    }
                                }
                            },
                            textStyle = TextStyle(fontSize = 30.sp),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            contentPadding = PaddingValues(horizontal = 25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                TextButton(onClick = {}) {
                    Text(stringResource(Res.string.auth_verification_not_receive))
                }
                CustomButton(title = stringResource(Res.string.button_send)) {
                    onAction(verificationCode.joinToString(separator = ""))
                }

            }
        }

    }
}