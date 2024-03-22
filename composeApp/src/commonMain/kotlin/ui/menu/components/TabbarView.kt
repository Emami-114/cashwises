package ui.menu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TabbarView(onClick: () -> Unit) {
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

            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.LOGIN.title,
                icon = TabBarEnum.LOGIN.icon
            ) {
                navigator.push(RegistrationScreen())
            }

            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.PROFILE.title,
                icon = TabBarEnum.PROFILE.icon
            ) {}
            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.SETTING.title,
                icon = TabBarEnum.SETTING.icon
            ) {}

            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.IMPRINT.title,
                icon = TabBarEnum.IMPRINT.icon
            ) {}

            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.PRIVACYPOLICY.title,
                icon = TabBarEnum.PRIVACYPOLICY.icon
            ) {}

            Spacer(modifier = Modifier.height(50.dp))

            TabBarItem(
                modifier = Modifier.height(60.dp),
                title = TabBarEnum.LOGOUT.title,
                icon = TabBarEnum.LOGOUT.icon
            ) {}
        }
    }

}

enum class TabBarEnum {
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