package utils

internal expect object LocalPushNotification {
    fun requestAuthorization(completion: (Boolean) -> Unit)
    fun schedule(pushNotificationModel: PushNotificationModel)
    fun getPendingRequestCount(pendingCount: (Int) -> Unit)
    fun removeAllRequest()
}

class PushNotificationModel(
    val identifier: String,
    val title: String,
    val body: String,
    val timeInterval: Double,
    val repeats: Boolean,
)