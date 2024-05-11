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
import androidx.compose.ui.platform.LocalView
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
    private val context: Context = AndroidApp.INSTANCE.applicationContext

    @SuppressLint("ScheduleExactAlarm")
    actual fun schedule(pushNotificationModel: PushNotificationModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        getPendingRequestCount { pendingCount ->
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("title", pushNotificationModel.title)
                putExtra("content", pushNotificationModel.body)
                putExtra("channel_id", pushNotificationModel.identifier)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                pendingCount,
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
    }

    actual fun getPendingRequestCount(pendingCount: (Int) -> Unit) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var count = 0
        val maxRequestCode = 10
        for (requestCode in 0 until maxRequestCode) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntents = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pendingIntents != null) {
                count++
            }
        }
        pendingCount(count)
    }

    actual fun removeAllRequest() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val maxRequestCode = 10
        for (requestCode in 0 until maxRequestCode) {
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntents = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pendingIntents != null) {
                alarmManager.cancel(pendingIntents)
                pendingIntents.cancel()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    actual fun requestAuthorization(completion: (Boolean) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.ACTIVITY,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            completion(true)
        } else {
            val requestCode = 123
            ActivityCompat.requestPermissions(
                MainActivity.ACTIVITY,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                requestCode
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestAuthorizationAndroid(activity: Activity, completion: (Boolean) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        completion(true)
    } else {
        val requestCode = 123
        ActivityCompat.requestPermissions(
            MainActivity.ACTIVITY,
            arrayOf(
                Manifest.permission.SCHEDULE_EXACT_ALARM,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.USE_EXACT_ALARM,
                Manifest.permission.WAKE_LOCK
            ),
            requestCode
        )
    }
}