package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class PostGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the XML layout for the introduction screen
        setContentView(R.layout.activity_post_game)

        // After a delay of 5 seconds, start the AgeGroupActivity
        Handler().postDelayed({
            val intent = Intent(this@PostGameActivity, PostQuestion1Activity::class.java)
            startActivity(intent)
            finish() // Prevent going back to the intro screen
        }, 8000) // 5000ms = 5 seconds
    }
}
