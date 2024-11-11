package ui.account.auth.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
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
import org.company.app.theme.cw_dark_red
import org.company.app.theme.cw_dark_whiteText
import org.jetbrains.compose.resources.stringResource
import ui.account.auth.registration.RegistrationState
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.components.CustomButton
import ui.components.CustomNotificationToast
import ui.components.ToastStatusEnum

@Composable
fun VerificationView(
    viewModel: RegistrationViewModel,
    uiState: RegistrationState,
    onAction: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        if (uiState.verificationCodeError != null) {
            CustomNotificationToast(
                modifier = Modifier.align(Alignment.BottomStart),
                status = ToastStatusEnum.ERROR,
                title = uiState.verificationCodeError
            ) { viewModel.doChangeOtpCode("") }
        }
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

            BasicTextField(
                value = uiState.otpCode,
                onValueChange = {
                    if (uiState.otpCode.length <= 4) {
                        viewModel.doChangeOtpCode(it)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),

                ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    repeat(4) { index ->
                        val number = when {
                            index >= uiState.otpCode.length -> ""
                            else -> uiState.otpCode[index]
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                number.toString(), fontSize = 25.sp, color = cw_dark_whiteText
                            )
                            Box(
                                modifier = Modifier.width(40.dp).height(2.dp).background(
                                    if (uiState.verificationCodeError == null)
                                        cw_dark_whiteText
                                    else cw_dark_red
                                )
                            )
                        }
                    }
                }
            }
            TextButton(onClick = {}) {
                Text(stringResource(Res.string.auth_verification_not_receive))
            }
            CustomButton(title = stringResource(Res.string.button_send)) {
                focusManager.clearFocus()
                onAction()
            }

        }


    }
}