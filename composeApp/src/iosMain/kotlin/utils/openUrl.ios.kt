package utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIUserNotificationTypeAlert
import platform.UIKit.currentUserNotificationSettings
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound.Companion.defaultSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

internal actual fun openUrl(url: String?) {
//    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
//    UIApplication.sharedApplication.openURL(nsUrl)

    val center = UNUserNotificationCenter.currentNotificationCenter()
    center.requestAuthorizationWithOptions(
        UNAuthorizationOptionBadge or UNAuthorizationOptionAlert or UNAuthorizationOptionSound
    ) { _, _ -> }

    val content = UNMutableNotificationContent()
    content.setTitle("Hallo")
    content.setBody("Hallo")
    content.setSound(defaultSound)
    val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(65.0, repeats = true)
    val request = UNNotificationRequest.requestWithIdentifier(
        "NOTIFICATION_REQUEST",
        content = content,
        trigger = trigger
    )

    center.addNotificationRequest(request) { error ->
        if (error != null) {
            println("Notification request completed with error: $error")
        }
    }
}