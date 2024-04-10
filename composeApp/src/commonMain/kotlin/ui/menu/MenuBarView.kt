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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.account.auth.registration.RegistrationScreen
import ui.components.CustomBackgroundView
import ui.menu.components.MenuBarItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MenuBarView(onClick: () -> Unit) {
    val navigator: Navigator = LocalNavigator.currentOrThrow
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).padding(20.dp)
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(DrawableResource("compose-multiplatform.xml")),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
//                Icon(
//                    Icons.Default.Close,
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.secondary,
//                    modifier = Modifier.size(40.dp).clickable { onClick() })
            }

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.LOGIN.title,
                icon = MenuBarEnum.LOGIN.icon
            ) {
                navigator.push(RegistrationScreen())
            }

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.PROFILE.title,
                icon = MenuBarEnum.PROFILE.icon
            ) {}
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
                title = MenuBarEnum.PRIVACYPOLICY.title,
                icon = MenuBarEnum.PRIVACYPOLICY.icon
            ) {}

            Spacer(modifier = Modifier.height(50.dp))

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.LOGOUT.title,
                icon = MenuBarEnum.LOGOUT.icon
            ) {}
        }
    }

}

enum class MenuBarEnum {
    LOGIN(title = "Login", icon = Icons.AutoMirrored.Filled.Login),
    PROFILE(title = "Profile", icon = Icons.Default.Person),
    SETTING(title = "Setting", icon = Icons.Default.Settings),
    IMPRINT(title = "Imprint", icon = Icons.AutoMirrored.Filled.MenuBook),
    PRIVACYPOLICY(title = "Privacy Policy", icon = Icons.Default.AdminPanelSettings),
    LOGOUT(title = "Log out", icon = Icons.AutoMirrored.Filled.Logout);

    var title: String
    var icon: ImageVector

    constructor(title: String, icon: ImageVector) {
        this.title = title
        this.icon = icon
    }

}