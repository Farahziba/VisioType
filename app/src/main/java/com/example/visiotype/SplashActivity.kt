package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set splash screen layout
        setContentView(R.layout.activity_splash)

        // Delay for 2 seconds before going to IntroductionActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, IntroductionActivity::class.java))
            finish()
        }, 2000)
    }
}
