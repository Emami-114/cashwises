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
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import ui.components.CustomButton
import ui.components.CustomTextField
import ui.components.customModiefier.noRippleClickable

@Composable
fun LogInView(toPasswordForget: () -> Unit) {
    val viewModel: LoginViewModel = koinInject()
    val uiState by viewModel.state.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var textExample by remember { mutableStateOf("") }
    var passwordExample by remember { mutableStateOf("") }
    val navigator = LocalNavigator.currentOrThrow
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
                navigator.pop()
            }

            uiState.errorMessage.isNullOrEmpty().not() -> {

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
                        onValueChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
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
                            keyboardType = KeyboardType.Email
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()

                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    CustomButton(title = "Login") {
                        viewModel.onEvent(LoginEvent.OnLogin)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Password forget?",
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