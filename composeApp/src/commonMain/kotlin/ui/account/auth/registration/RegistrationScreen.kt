package ui.account.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.sp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.accept_data_protection_description
import cashwises.composeapp.generated.resources.accept_data_protection_error
import cashwises.composeapp.generated.resources.btn_login
import cashwises.composeapp.generated.resources.btn_register
import cashwises.composeapp.generated.resources.eye
import cashwises.composeapp.generated.resources.eye_off
import cashwises.composeapp.generated.resources.invalid_email_address_error
import cashwises.composeapp.generated.resources.lock
import cashwises.composeapp.generated.resources.mail
import cashwises.composeapp.generated.resources.password
import cashwises.composeapp.generated.resources.password_confirm
import cashwises.composeapp.generated.resources.password_confirm_not_match_error
import cashwises.composeapp.generated.resources.password_forget
import cashwises.composeapp.generated.resources.password_forget_desc
import cashwises.composeapp.generated.resources.password_required_error
import cashwises.composeapp.generated.resources.password_reset
import cashwises.composeapp.generated.resources.privacy_policy_placeholder
import cashwises.composeapp.generated.resources.terms_of_use_placeholder
import cashwises.composeapp.generated.resources.user
import cashwises.composeapp.generated.resources.username
import cashwises.composeapp.generated.resources.username_required_error
import org.company.app.theme.cw_dark_primary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.components.CustomButton
import ui.components.CustomHyperlinkView
import ui.components.CustomSwitch
import ui.components.CustomTextField
import ui.components.customModiefier.noRippleClickable

@Composable
fun PasswordForget(backToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize().padding(10.dp).padding(horizontal = 10.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text(
                stringResource(Res.string.password_forget_desc),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "E-mail",
                label = { Text("E-mail") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.mail),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            CustomButton(title = stringResource(Res.string.password_reset)) {
            }
            Text(
                stringResource(Res.string.btn_login),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth().noRippleClickable {
                    backToLogin()
                })


        }
    }
}

@Composable
fun RegistrationView(
    viewModel: RegistrationViewModel,
    uiState: RegistrationState,
    toVerification: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            uiState.isRegistrationSuccess -> {
                toVerification()
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(10.dp)
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    CustomTextField(
                        value = uiState.nameText,
                        onValueChange = {
                            viewModel.onRegisterEvent(
                                RegistrationEvent.OnUserNameChange(
                                    it
                                )
                            )
                        },
                        errorText = if (!uiState.nameError.isNullOrEmpty()) stringResource(Res.string.username_required_error) else null,
                        placeholder = stringResource(Res.string.username),
                        label = { Text(stringResource(Res.string.username)) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.user),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        })
                    CustomTextField(
                        value = uiState.emailText,
                        onValueChange = {
                            viewModel.onRegisterEvent(
                                RegistrationEvent.OnEmailChange(
                                    it
                                )
                            )
                        },
                        placeholder = "E-mail",
                        label = { Text("E-mail") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.mail),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email
                        ),
                        errorText = if (!uiState.emailError.isNullOrEmpty())
                            stringResource(Res.string.invalid_email_address_error) else null
                    )

                    CustomTextField(
                        value = uiState.passwordText,
                        onValueChange = {
                            viewModel.onRegisterEvent(
                                RegistrationEvent.OnPasswordChange(
                                    it
                                )
                            )
                        },
                        placeholder = stringResource(Res.string.password),
                        label = { Text(stringResource(Res.string.password)) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.lock),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        },
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
                        errorText = if (!uiState.passwordError.isNullOrEmpty()) stringResource(Res.string.password_required_error) else null,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    CustomTextField(
                        value = uiState.passwordConfirm,
                        onValueChange = {
                            viewModel.onRegisterEvent(
                                RegistrationEvent.OnPasswordConfirmChange(
                                    it
                                )
                            )
                        },
                        placeholder = stringResource(Res.string.password_confirm),
                        label = { Text(stringResource(Res.string.password_confirm)) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.mail),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp)
                            )
                        },
                        errorText = if (!uiState.passwordConfirmError.isNullOrEmpty()) stringResource(
                            Res.string.password_confirm_not_match_error
                        ) else null,
                        trailingIcon = {
                            Icon(
                                painter = if (passwordConfirmVisible) painterResource(Res.drawable.eye) else painterResource(
                                    Res.drawable.eye_off
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(26.dp).noRippleClickable {
                                    passwordConfirmVisible = !passwordConfirmVisible
                                })
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        CustomSwitch(
                            modifier = Modifier,
                            textView = {
                                CustomHyperlinkView(
                                    modifier = Modifier,
                                    fullText = stringResource(Res.string.accept_data_protection_description),
                                    linksText = listOf(
                                        stringResource(Res.string.privacy_policy_placeholder),
                                        stringResource(Res.string.terms_of_use_placeholder)
                                    ),
                                    hyperLinks = listOf(
                                        "https://www.youtube.com/watch?v=-fouArUd56I&ab_channel=Stevdza-San",
                                        "https://www.youtube.com/watch?v=-fouArUd56I&ab_channel=Stevdza-San"
                                    ),
                                    linkTextColor = cw_dark_primary,
                                    fontSize = 16.sp
                                )
                            },
                            value = uiState.acceptedDataProtection,
                            errorText = if (uiState.acceptedDataProtectionError.isNullOrEmpty()
                                    .not()
                            ) stringResource(Res.string.accept_data_protection_error) else null
                        ) {
                            viewModel.onRegisterEvent(
                                RegistrationEvent.OnAcceptedDataProtectChange(
                                    it
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomButton(title = stringResource(Res.string.btn_register)) {
                        viewModel.onRegisterEvent(RegistrationEvent.OnRegistration)
                    }
                }
            }
        }
    }
}