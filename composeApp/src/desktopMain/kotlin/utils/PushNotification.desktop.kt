package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberTrayState
import javax.naming.Context

internal actual object LocalPushNotification {

    actual fun schedule(pushNotificationModel: PushNotificationModel) {
    }

    actual fun getPendingRequestCount(pendingCount: (Int) -> Unit) {
        TODO("Not yet implemented")
    }

    actual fun removeAllRequest() {
    }

    actual fun requestAuthorization(completion: (Boolean) -> Unit) {
    }
}