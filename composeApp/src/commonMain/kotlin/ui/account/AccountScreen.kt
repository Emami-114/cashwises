package ui.account

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
import androidx.compose.ui.unit.dp
import cashwises.composeapp.generated.resources.Res
import cashwises.composeapp.generated.resources.book
import cashwises.composeapp.generated.resources.book_2
import cashwises.composeapp.generated.resources.btn_login
import cashwises.composeapp.generated.resources.compose_multiplatform
import cashwises.composeapp.generated.resources.heart
import cashwises.composeapp.generated.resources.heart_fill
import cashwises.composeapp.generated.resources.imprint
import cashwises.composeapp.generated.resources.log_out
import cashwises.composeapp.generated.resources.login
import cashwises.composeapp.generated.resources.logout
import cashwises.composeapp.generated.resources.privacy_policy
import cashwises.composeapp.generated.resources.profile
import cashwises.composeapp.generated.resources.settings
import cashwises.composeapp.generated.resources.successfully_logout
import cashwises.composeapp.generated.resources.user
import cashwises.composeapp.generated.resources.wish_list
import data.repository.UserRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.AppScreen
import ui.components.CustomBackgroundView
import ui.components.CustomToast
import ui.menu.components.MenuBarItem

@Composable
fun AccountView(onNavigate: (String) -> Unit) {
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
                title = MenuBarEnum.WISHLIST.title,
                icon = MenuBarEnum.WISHLIST.icon
            ) {
                onNavigate(AppScreen.WishList.route)
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

enum class MenuBarEnum(var title: StringResource, var icon: DrawableResource) {
    LOGIN(title = Res.string.btn_login, icon = Res.drawable.login),
    PROFILE(title = Res.string.profile, icon = Res.drawable.user),
    WISHLIST(title = Res.string.wish_list, icon = Res.drawable.heart_fill),
    SETTING(title = Res.string.settings, icon = Res.drawable.settings),
    IMPRINT(title = Res.string.imprint, icon = Res.drawable.book),
    PRIVACY_POLICY(title = Res.string.privacy_policy, icon = Res.drawable.book_2),
    LOGOUT(title = Res.string.log_out, icon = Res.drawable.logout);

}