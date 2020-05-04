package pl.lejdi.fakeroutesgenerator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private var serviceStarted = false
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.start_button)
        setButtonClickListener()
    }

    private fun setButtonClickListener()
    {
        button.setOnClickListener {
            if (!serviceStarted) {
                serviceStarted = true
                button.text = "STOP"
                runService()
            } else {
                serviceStarted = false
                button.text = "START"
                abortService()
            }
        }
    }

    private fun runService()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2000)
        }

        startForegroundService(Intent(applicationContext, FakeLocationService::class.java))
    }

    private fun abortService()
    {
        stopService(Intent(this, FakeLocationService::class.java))
    }
}
