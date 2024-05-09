package utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.util.trace
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.datetime.Clock
import org.emami.cashwises.AlarmReceiver
import org.emami.cashwises.AndroidApp
import org.emami.cashwises.MainActivity
import org.emami.cashwises.R

@SuppressLint("StaticFieldLeak")
internal actual object LocalPushNotification {
    val context = AndroidApp.INSTANCE.applicationContext

    class NotificationReceiver(private val pushNotificationModel: PushNotificationModel) :
        BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context?, intent: Intent?) {
            val title = intent?.getStringExtra("title") ?: ""
            val content = intent?.getStringExtra("content") ?: ""

            // Hier können Sie die Benachrichtigung anzeigen
            // Verwenden Sie dazu NotificationManager und NotificationCompat

            val notificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()

            notificationManager.notify(1, notification)
        }

    }

    @SuppressLint("ScheduleExactAlarm")
    actual fun schedule(pushNotificationModel: PushNotificationModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("title", "title")
            putExtra("content", "content")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val triggerAtMillis =
            System.currentTimeMillis() + pushNotificationModel.timeInterval.toLong()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )

    }

    actual fun getPendingRequestCount(pendingCount: (Int) -> Unit) {
        TODO("Not yet implemented")
    }

    actual fun removeRequest(identifier: String) {
    }

    actual fun removeAllRequest() {
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    actual fun requestAuthorization(completion: (Boolean) -> Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            completion(true)
//        } else {
        val requestCode = 123
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            requestCode
        )
//        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestAuthorizationAndroid(activity: Activity, completion: (Boolean) -> Unit) {
    // Überprüfen, ob die Berechtigung bereits gewährt wurde
//    if (ActivityCompat.checkSelfPermission(
//            activity,
//            Manifest.permission.POST_NOTIFICATIONS
//        ) == PackageManager.PERMISSION_GRANTED
//    ) {
//        // Berechtigung bereits gewährt
//        completion(true)
//    } else {
    // Berechtigung noch nicht gewährt, um sie anzufordern
    val requestCode = 123 // Ein beliebiger Anforderungscode
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(
            Manifest.permission.SCHEDULE_EXACT_ALARM,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.USE_EXACT_ALARM,
            Manifest.permission.WAKE_LOCK
        ),
        requestCode
    )
//    }
}