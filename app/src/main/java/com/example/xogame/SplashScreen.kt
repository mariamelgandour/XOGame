package com.example.xogame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.util.Log

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        Log.d("SplashScreen", "Splash Screen Started")

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("SplashScreen", "Navigating to XOGame")
            val intent = Intent(this, XOGame::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}
