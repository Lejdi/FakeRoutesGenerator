package pl.lejdi.fakeroutesgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRoute()
    }

    private fun getRoute()
    {
        val gson = Gson()
        val route = gson.fromJson(HardcodedRoutes.r3, Route::class.java)
    }
}
