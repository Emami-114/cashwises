package utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

internal actual object LocalPushNotification {
    private val center = UNUserNotificationCenter.currentNotificationCenter()
    actual fun schedule(pushNotificationModel: PushNotificationModel) {
        val content = UNMutableNotificationContent()
        content.setTitle(pushNotificationModel.title)
        content.setBody(pushNotificationModel.body)
        content.setSound(UNNotificationSound.defaultSound)
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            pushNotificationModel.timeInterval,
            repeats = pushNotificationModel.repeats
        )
        val request = UNNotificationRequest.requestWithIdentifier(
            pushNotificationModel.identifier,
            content = content,
            trigger = trigger
        )

        center.addNotificationRequest(request) { error ->
            if (error != null) {
                println("Notification request completed with error: $error")
            }
        }
    }

    actual fun getPendingRequestCount(pendingCount: (Int) -> Unit) {
        center.getPendingNotificationRequestsWithCompletionHandler { pending ->
            pendingCount(pending?.size ?: 0)

        }
    }

    actual fun removeRequest(identifier: String) {
        center.removePendingNotificationRequestsWithIdentifiers(identifiers = listOf(identifier))
    }

    actual fun removeAllRequest() {
        center.removeAllPendingNotificationRequests()
    }

    actual fun requestAuthorization(completion: (Boolean) -> Unit) {
        center.requestAuthorizationWithOptions(
            UNAuthorizationOptionBadge or UNAuthorizationOptionAlert or UNAuthorizationOptionSound
        ) { isSuccess, error ->
            if (error == null) {
                completion(isSuccess)
            }
        }
    }
}