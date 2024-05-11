package ui.account.auth

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.btn_login
import cashwises.composeapp.generated.resources.btn_register
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.AppConstants
import ui.AppScreen
import ui.account.auth.login.LogInView
import ui.account.auth.login.LoginViewModel
import ui.account.auth.registration.PasswordForget
import ui.account.auth.registration.RegistrationView
import ui.account.auth.registration.viewModel.RegistrationViewModel
import ui.account.auth.verification.VerificationView
import ui.components.CustomBackgroundView
import ui.components.CustomButton
import ui.components.CustomPopUp
import ui.components.CustomTopAppBar

@Composable
fun AuthView(onNavigate: (String) -> Unit) {
    val registerViewModel: RegistrationViewModel = koinInject()
    val registeUiState by registerViewModel.state.collectAsState()
    val loginViewModel: LoginViewModel = koinInject()
    val loginUiState by loginViewModel.state.collectAsState()
    var currentView by remember { mutableStateOf(AuthEnum.LOGIN) }
    var animationState by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CustomTopAppBar(title = currentView.name, backButtonAction = {
            onNavigate(AppConstants.BackClickRoute.route)
        })
    }, snackbarHost = {

    }) { paddingValue ->
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackgroundView()
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValue).padding(top = 20.dp)
            ) {
                if (currentView != AuthEnum.PASSWORDFORGET && !registeUiState.isRegistrationSuccess) {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)) {
                        CustomButton(
                            modifier = Modifier.weight(1f).padding(horizontal = 3.dp)
                                .height(40.dp),
                            color = if (currentView.name == AuthEnum.LOGIN.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            title = stringResource(Res.string.btn_login)
                        ) { currentView = AuthEnum.LOGIN }
                        CustomButton(
                            modifier = Modifier.weight(1f).padding(horizontal = 3.dp)
                                .height(40.dp),
                            color = if (currentView.name == AuthEnum.REGISTRATION.name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                            title = stringResource(Res.string.btn_register)
                        ) {
                            currentView = AuthEnum.REGISTRATION
                            animationState = true
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                when {
                    registeUiState.isLoading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary)
                        }
                    }

                    registeUiState.errorMessage.isNullOrEmpty().not() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CustomPopUp(
                                present = registeUiState.errorMessage != null,
                                message = registeUiState.errorMessage ?: "",
                            )
                        }
                    }

                    registeUiState.isRegistrationSuccess -> {
                        VerificationView(errorMessage = registeUiState.verificationCodeError) {
                            registerViewModel.doVerification(it) {
                                onNavigate("")
                            }
                        }
                    }

                    else -> {
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
                                    LogInView(
                                        viewModel = loginViewModel,
                                        uiState = loginUiState,
                                        toPasswordForget = {
                                            currentView = AuthEnum.PASSWORDFORGET
                                        }, toHome = {
                                            onNavigate("")
                                        })
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
                                    RegistrationView(viewModel = registerViewModel,
                                        uiState = registeUiState,
                                        toVerification = {
                                            println("Test: to Verification ")
                                        })
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

            }

        }
    }
}

enum class AuthEnum() {
    LOGIN, REGISTRATION, PASSWORDFORGET
}