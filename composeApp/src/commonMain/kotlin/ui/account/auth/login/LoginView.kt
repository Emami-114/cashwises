package ui.account.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.btn_login
import cashwises.composeapp.generated.resources.eye
import cashwises.composeapp.generated.resources.eye_off
import cashwises.composeapp.generated.resources.invalid_email_address_error
import cashwises.composeapp.generated.resources.lock
import cashwises.composeapp.generated.resources.mail
import cashwises.composeapp.generated.resources.password
import cashwises.composeapp.generated.resources.password_forget
import cashwises.composeapp.generated.resources.password_required_error
import cashwises.composeapp.generated.resources.successfully_login
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomTextField
import ui.components.CustomToast
import ui.components.customModiefier.noRippleClickable

@Composable
fun LogInView(
    viewModel: LoginViewModel,
    uiState: LoginState,
    toPasswordForget: () -> Unit,
    toHome: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
//        CustomBackgroundView()
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            uiState.errorMessage.isNullOrEmpty().not() -> {
                CustomPopUp(true, onDismissDisable = true, message = uiState.errorMessage ?: "")
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    CustomTextField(
                        value = uiState.emailText,
                        onValueChange = { viewModel.onEvent(LoginEvent.OnEmailChange(it)) },
                        placeholder = "E-mail",
                        label = { Text("E-mail") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.mail),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        errorText = if (uiState.emailError.isNullOrEmpty().not()
                        ) stringResource(Res.string.invalid_email_address_error) else null,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email
                        )
                    )
                    CustomTextField(
                        value = uiState.passwordText,
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
                        placeholder = stringResource(Res.string.password),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.lock),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        errorText = if (uiState.passwordError.isNullOrEmpty().not()) stringResource(
                            Res.string.password_required_error
                        ) else null,
                        label = { Text(stringResource(Res.string.password)) },
                        trailingIcon = {
                            Icon(
                                painter = if (passwordVisible) painterResource(Res.drawable.eye) else painterResource(
                                    Res.drawable.eye_off
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp).noRippleClickable {
                                    passwordVisible = !passwordVisible
                                })
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    CustomButton(title = stringResource(Res.string.btn_login)) {
                        viewModel.onEvent(LoginEvent.OnLogin)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        stringResource(Res.string.password_forget),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxWidth().noRippleClickable {
                            toPasswordForget()
                        })

                    if (uiState.isLoginSuccess) {
                        CustomToast(
                            modifier = Modifier,
                            title = stringResource(Res.string.successfully_login)
                        ) {
                            toHome()
                            viewModel.onEvent(LoginEvent.OnSetDefaultState)
                        }
                    }
                }
            }
        }


    }
}