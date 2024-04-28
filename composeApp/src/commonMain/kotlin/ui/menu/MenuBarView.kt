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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.compose_multiplatform
import compose.icons.TablerIcons
import compose.icons.tablericons.Book
import compose.icons.tablericons.Login
import compose.icons.tablericons.Logout
import compose.icons.tablericons.Settings
import compose.icons.tablericons.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.account.auth.AuthScreen
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
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.LOGIN.title,
                icon = MenuBarEnum.LOGIN.icon
            ) {
                navigator.push(AuthScreen())
            }

            MenuBarItem(
                modifier = Modifier.height(60.dp),
                title = MenuBarEnum.PROFILE.title,
                icon = MenuBarEnum.PROFILE.icon
            ) {

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

enum class MenuBarEnum(var title: String, var icon: ImageVector) {
    LOGIN(title = "Login", icon = TablerIcons.Login),
    PROFILE(title = "Profile", icon = TablerIcons.User),
    SETTING(title = "Setting", icon = TablerIcons.Settings),
    IMPRINT(title = "Imprint", icon = TablerIcons.Book),
    PRIVACYPOLICY(title = "Privacy Policy", icon = TablerIcons.Book),
    LOGOUT(title = "Log out", icon = TablerIcons.Logout);

}