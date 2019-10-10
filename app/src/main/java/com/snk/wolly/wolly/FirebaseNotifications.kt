package com.snk.wolly.wolly

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FirebaseNotifications : FirebaseMessagingService() {
    private val ADMIN_CHANNEL_ID = "admin_channel"
    //https://medium.com/mindorks/send-device-to-device-push-notification-using-firebase-cloud-messaging-without-using-external-769476c79ffd
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val intent = Intent(this, PickRecyclable::class.java)
        val notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt(3000)

        /*Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them.*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT
        )

        val largeIcon = BitmapFactory.decodeResource(
                resources,
                R.mipmap.recycle
        )

        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.mipmap.recycle)
                .setLargeIcon(largeIcon)
                .setContentTitle(p0.data["title"])
                .setContentText(p0.data["message"])
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(pendingIntent)


        notificationManager.notify(notificationID, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager?) {
        val adminChannelName = "New notification"
        val adminChannelDescription = "Device to devie notification"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager?.createNotificationChannel(adminChannel)
    }
}
