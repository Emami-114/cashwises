package ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import compose.icons.TablerIcons
import compose.icons.tablericons.Home
import kotlinx.coroutines.launch
import ui.components.CustomBackgroundView
import ui.components.CustomTextField
import ui.components.CustomToast
import utils.LocalPushNotification
import utils.PushNotificationModel

class NotificationScreen : Screen {
    @Composable
    override fun Content() {
        NotificationView { }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(onNavigate: (String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var pendingCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val notification = LocalPushNotification
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()

        Column(verticalArrangement = Arrangement.Bottom) {
            Spacer(modifier = Modifier.height(200.dp))
            CustomTextField(value = title, onValueChange = { title = it })
            CustomTextField(value = body, onValueChange = { body = it })
            Button(onClick = {
                notification.schedule(
                    PushNotificationModel(
                        identifier = "$title $body",
                        title = title,
                        body = body,
                        repeats = false,
                        timeInterval = 10.0
                    )
                )

//                notification.getPendingRequestCount { count ->
////                    scope.launch {
//                        pendingCount = count
////                    }
//                }
            }) {
                Text("Notification")
            }
            Text("$pendingCount")
            CustomToast()
        }
    }

}

object NotificationScreenTab : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = NotificationScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }

    override val options: TabOptions
        @Composable get() {
            val title = "NOTIFICATION"
            val icon = rememberVectorPainter(TablerIcons.Home)
            return remember {
                TabOptions(
                    index = 2u, title = title, icon = icon
                )
            }
        }

}