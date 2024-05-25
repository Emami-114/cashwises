package ui.menu

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.compose_multiplatform
import cashwises.composeapp.generated.resources.successfully_logout
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import compose.icons.tablericons.Login
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Settings
import compose.icons.tablericons.User
import data.repository.UserRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.AppScreen
import ui.components.CustomBackgroundView
import ui.components.CustomToast
import ui.menu.components.MenuBarItem

@Composable
fun MenuBarView(onNavigate: (String) -> Unit) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState(0)
    var showLogOutToast by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        if (showLogOutToast) {
            CustomToast(
                modifier = Modifier.padding(bottom = 50.dp),
                title = stringResource(Res.string.successfully_logout)
            ) { showLogOutToast = false }
        }
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).padding(20.dp)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
            if (!UserRepository.INSTANCE.userIsLogged) {
                MenuBarItem(
                    modifier = Modifier.height(60.dp),
                    title = MenuBarEnum.LOGIN.title,
                    icon = MenuBarEnum.LOGIN.icon
                ) {
                    onNavigate(AppScreen.Authentication.route)
                }
            }

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.PROFILE.title,
                icon = MenuBarEnum.PROFILE.icon
            ) {
                onNavigate(AppScreen.Profile.route)
            }
            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.SETTING.title,
                icon = MenuBarEnum.SETTING.icon
            ) {}

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.IMPRINT.title,
                icon = MenuBarEnum.IMPRINT.icon
            ) {}

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.PRIVACY_POLICY.title,
                icon = MenuBarEnum.PRIVACY_POLICY.icon
            ) {}

            if (UserRepository.INSTANCE.userIsLogged) {
                Spacer(modifier = Modifier.height(50.dp))
                MenuBarItem(
                    modifier = Modifier.height(60.dp),
                    title = MenuBarEnum.LOGOUT.title,
                    icon = MenuBarEnum.LOGOUT.icon
                ) {
                    scope.launch {
                        UserRepository.INSTANCE.logout().let { isSuccess ->
                            if (isSuccess) {
                                showLogOutToast = true
                            }
                        }
                    }
                }
            }
        }
    }

}

enum class MenuBarEnum(var title: String, var icon: ImageVector) {
    LOGIN(title = "Login", icon = TablerIcons.Login),
    PROFILE(title = "Profile", icon = TablerIcons.User),
    SETTING(title = "Setting", icon = TablerIcons.Settings),
    IMPRINT(title = "Imprint", icon = TablerIcons.Book),
    PRIVACY_POLICY(title = "Privacy Policy", icon = TablerIcons.Book),
    LOGOUT(title = "Log out", icon = TablerIcons.Logout);

}