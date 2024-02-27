package ui.account.auth.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
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
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
                        CustomButton(
                            modifier = Modifier.weight(1f).padding(horizontal = 3.dp).height(40.dp),
                            color = if (currentView.name == AuthEnum.LOGIN.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            title = "Login"
                        ) { currentView = AuthEnum.LOGIN }
                        CustomButton(
                            modifier = Modifier.weight(1f).padding(horizontal = 3.dp).height(40.dp),
                            color = if (currentView.name == AuthEnum.REGISTRATION.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            title = "Registration"
                        ) {
                            currentView = AuthEnum.REGISTRATION
                            animationState = true
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

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
                                LogInView(navigator)
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
                    }

                }
            }

//            RegistrationView(navigator)
        }
    }
}

enum class AuthEnum() {
    LOGIN,
    REGISTRATION
}

@Composable
fun LogInView(navigator: Navigator) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
//        CustomBackgroundView()
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                text = email,
                onchangeText = { email = it },
                placeholder = "E-mail",
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                }
            )
            CustomTextField(
                text = password,
                onchangeText = { password = it },
                placeholder = "Password",
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            passwordVisible = !passwordVisible
                        }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

            )
            Spacer(modifier = Modifier.height(15.dp))

            CustomButton(title = "Login") {}

        }
    }
}

@Composable
fun RegistrationView(navigator: Navigator) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var switch by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
//        CustomBackgroundView()
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            CustomTextField(
                text = userName,
                onchangeText = { userName = it },
                placeholder = "Username",
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )
            CustomTextField(
                text = email,
                onchangeText = { email = it },
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
                text = password,
                onchangeText = { password = it },
                placeholder = "Password",
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            passwordVisible = !passwordVisible
                        }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            CustomTextField(
                text = password,
                onchangeText = { password = it },
                placeholder = "Password repeat",
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.noRippleClickable {
                            passwordVisible = !passwordVisible
                        }
                    )
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
                Switch(checked = switch, onCheckedChange = {
                    switch = it
                }, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(10.dp))
            CustomButton(title = "Registration") {

            }

        }

    }


}