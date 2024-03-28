package ui.account.auth.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import ui.account.auth.login.LogInView
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomTextField
import ui.components.CustomTopAppBar
import ui.components.customModiefier.noRippleClickable

class RegistrationScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var currentView by remember { mutableStateOf(AuthEnum.LOGIN) }
        var animationState by remember { mutableStateOf(false) }
        Scaffold(topBar = {
            CustomTopAppBar(title = currentView.name, backButtonAction = {
                navigator.pop()
            })
        }) { paddingValue ->
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValue).padding(top = 20.dp)
                ) {
                    if (currentView != AuthEnum.PASSWORDFORGET) {
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
                            CustomButton(
                                modifier = Modifier.weight(1f).padding(horizontal = 3.dp)
                                    .height(40.dp),
                                color = if (currentView.name == AuthEnum.LOGIN.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                title = "Login"
                            ) { currentView = AuthEnum.LOGIN }
                            CustomButton(
                                modifier = Modifier.weight(1f).padding(horizontal = 3.dp)
                                    .height(40.dp),
                                color = if (currentView.name == AuthEnum.REGISTRATION.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                title = "Registration"
                            ) {
                                currentView = AuthEnum.REGISTRATION
                                animationState = true
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    when (currentView) {
                        AuthEnum.LOGIN -> {
                            AnimatedVisibility(
                                visible = currentView == AuthEnum.LOGIN,
                                enter = slideInHorizontally(animationSpec = tween(1000)),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 300)
                                ) + shrinkVertically(
                                    animationSpec = tween(delayMillis = 300)
                                )
                            ) {
                                LogInView() {
                                    currentView = AuthEnum.PASSWORDFORGET
                                }
                            }
                        }

                        AuthEnum.REGISTRATION -> {
                            AnimatedVisibility(
                                visible = currentView == AuthEnum.REGISTRATION,
                                enter = slideInHorizontally(animationSpec = tween(1000)),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { -it },
                                    animationSpec = tween(durationMillis = 300)
                                ) + shrinkVertically(
                                    animationSpec = tween(delayMillis = 300)
                                )
                            ) {
                                RegistrationView(navigator)
                            }
                        }

                        AuthEnum.PASSWORDFORGET -> {
                            PasswordForget() {
                                currentView = AuthEnum.LOGIN
                            }
                        }
                    }

                }
            }

//            RegistrationView(navigator)
        }
    }
}

enum class AuthEnum() {
    LOGIN, REGISTRATION, PASSWORDFORGET
}

@Composable
fun PasswordForget(backToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Text(
                "Password vergessen? Kein Problem! " + "Tragen hier deine E-mail-Adresse ein und wir schicken Dir " + "eine E-mail an die verknüpfte Adresse. Damit kann das " + "Password zurückgesetzt werden.",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "E-mail",
                leadingIcon = {
                    Icon(Icons.Default.Mail, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                )
            )
//            Spacer(modifier = Modifier.height(15.dp))
            CustomButton(title = "Password reset") {

            }
            Text("Login",
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
fun RegistrationView(navigator: Navigator) {
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }
    val viewMode: RegistrationViewModel = koinInject()
    val uiState by viewMode.registrationState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
//        CustomBackgroundView()
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

            uiState.errorMessage.isNullOrEmpty().not() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.errorMessage.orEmpty(),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    CustomTextField(value = uiState.nameText,
                        onValueChange = {
                            viewMode.onRegisterEvent(
                                RegistrationEvent.OnUserNameChange(
                                    it
                                )
                            )
                        },
                        placeholder = "Username",
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        })
                    CustomTextField(
                        value = uiState.emailText,
                        onValueChange = {
                            viewMode.onRegisterEvent(
                                RegistrationEvent.OnEmailChange(
                                    it
                                )
                            )
                        },
                        placeholder = "E-mail",
                        leadingIcon = {
                            Icon(Icons.Default.Mail, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email
                        )
                    )

                    CustomTextField(
                        value = uiState.passwordText,
                        onValueChange = {
                            viewMode.onRegisterEvent(
                                RegistrationEvent.OnPasswordChange(
                                    it
                                )
                            )
                        },
                        placeholder = "Password",
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                modifier = Modifier.noRippleClickable {
                                    passwordVisible = !passwordVisible
                                })
                        },
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
                            viewMode.onRegisterEvent(
                                RegistrationEvent.OnPasswordConfirmChange(
                                    it
                                )
                            )
                        },
                        placeholder = "Password repeat",
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            Icon(if (passwordConfirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                modifier = Modifier.noRippleClickable {
                                    passwordConfirmVisible = !passwordConfirmVisible
                                })
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Ich akzeptiere die Datenschutzerklärung und dir Nutzungbedingungen",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(6f)
                        )
                        Switch(
                            checked = uiState.acceptedDataProtection,
                            onCheckedChange = {
                                viewMode.onRegisterEvent(
                                    RegistrationEvent.OnAcceptedDataProtectChange(
                                        it
                                    )
                                )
                            }, modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomButton(title = "Registration") {
                        viewMode.onRegisterEvent(RegistrationEvent.OnRegistration)
                    }
                }
            }
        }

    }
}