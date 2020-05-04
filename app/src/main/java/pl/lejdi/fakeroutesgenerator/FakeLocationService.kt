package pl.lejdi.fakeroutesgenerator

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class FakeLocationService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
    }

    private fun createNotification(){
        val notifBuilder = NotificationCompat.Builder(this, "pl.lejdi.fakeroutesgenerator")
            .setOngoing(true)
            .setSmallIcon(R.drawable.common_google_signin_btn_text_dark)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val chan = NotificationChannel("pl.lejdi.fakeroutesgenerator", "Fake Locations", NotificationManager.IMPORTANCE_HIGH)
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.createNotificationChannel(chan)

        val notification = notifBuilder.build()
        notifManager.notify(2, notification)

        startForeground(2, notification)
    }
}