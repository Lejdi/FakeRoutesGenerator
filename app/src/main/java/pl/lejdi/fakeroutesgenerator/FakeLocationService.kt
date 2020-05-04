package pl.lejdi.fakeroutesgenerator

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.lang.Exception

class FakeLocationService : Service() {

    private lateinit var locationManager : LocationManager
    private lateinit var job : Job

    val PROVIDER_NAME = LocationManager.GPS_PROVIDER

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        createNotification()
        startGeneratingLocations()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopGeneratingLocations()
    }

    private fun createNotification(){
        val notifBuilder = NotificationCompat.Builder(this, "pl.lejdi.fakeroutesgenerator")
            .setOngoing(true)
            .setSmallIcon(R.drawable.common_google_signin_btn_text_dark)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(NotificationManager.IMPORTANCE_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val chan = NotificationChannel("pl.lejdi.fakeroutesgenerator", "Fake Locations", NotificationManager.IMPORTANCE_HIGH)
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.createNotificationChannel(chan)

        val notification = notifBuilder.build()

        startForeground(2, notification)
    }

    private fun startGeneratingLocations(){
        try{
            locationManager.addTestProvider(PROVIDER_NAME, false, false, false, false, false, true, true, 1, 1)
        } catch (e : Exception){
            e.printStackTrace()
        }
        locationManager.setTestProviderEnabled(PROVIDER_NAME, true)

        chopChop()
    }

    private fun stopGeneratingLocations(){
        job.cancel()
        locationManager.setTestProviderEnabled(PROVIDER_NAME, false)
        locationManager.removeTestProvider(PROVIDER_NAME)
    }

    private fun chopChop()
    {
        lateinit var route : Route
        val rng = (0..100).random()
        val gson = Gson()
        if(rng % 3 == 0) {
            route = gson.fromJson(HardcodedRoutes.r1, Route::class.java)
        }
        if(rng % 3 == 1) {
            route = gson.fromJson(HardcodedRoutes.r2, Route::class.java)
        }
        if(rng % 3 == 2) {
            route = gson.fromJson(HardcodedRoutes.r3, Route::class.java)
        }
        if(rng == 0){
            Log.i("Oh..", "Gods of RNG hates you :(")
        }

        job = GlobalScope.launch {
            withContext(Dispatchers.Main){
                route.verts.forEach {
                    delay(1000)
                    val loc = Location(PROVIDER_NAME)
                    loc.accuracy = 100.0f
                    loc.time = System.currentTimeMillis()
                    loc.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
                    loc.latitude = it.latitude
                    loc.longitude = it.longitude
                    loc.speed = 5.0f
                    locationManager.setTestProviderLocation(PROVIDER_NAME, loc)
                }
            }
        }
    }
}