package ui.account.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import cashwises.composeapp.generated.resources.invalid_email_address_error
import cashwises.composeapp.generated.resources.password
import cashwises.composeapp.generated.resources.password_forget
import cashwises.composeapp.generated.resources.password_required_error
import compose.icons.TablerIcons
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import compose.icons.tablericons.Lock
import compose.icons.tablericons.Mail
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomTextField
import ui.components.customModiefier.noRippleClickable

@Composable
fun LogInView(toPasswordForget: () -> Unit, toHome: () -> Unit) {
    val viewModel: LoginViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
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

            uiState.isLoginSuccess -> {
                toHome()
                viewModel.onEvent(LoginEvent.OnSetDefaultState)
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
                            Icon(TablerIcons.Mail, contentDescription = null)
                        },
                        errorText = if (uiState.emailError.isNullOrEmpty()
                                .not()
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
                            Icon(TablerIcons.Lock, contentDescription = null)
                        },
                        errorText = if (uiState.passwordError.isNullOrEmpty().not()) stringResource(
                            Res.string.password_required_error
                        ) else null,
                        label = { Text(stringResource(Res.string.password)) },
                        trailingIcon = {
                            Icon(if (passwordVisible) TablerIcons.Eye else TablerIcons.EyeOff,
                                contentDescription = null,
                                modifier = Modifier.noRippleClickable {
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

                }
            }
        }


    }
}