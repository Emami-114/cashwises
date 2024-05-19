package ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.account.auth.verification.VerificationView
import ui.components.CustomBackgroundView
import ui.components.CustomTextField
import ui.components.CustomToast
import utils.LocalPushNotification
import utils.PushNotificationModel

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
        CustomToast {  }
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

                notification.getPendingRequestCount { count ->
                        pendingCount = count
                }
            }) {
                Text("Notification")
            }
            Text("$pendingCount")
        }
    }
}