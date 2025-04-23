package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class ThankYouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the XML layout for the introduction screen
        setContentView(R.layout.activity_thank_you)

        // After a delay of 5 seconds, start the AgeGroupActivity
        Handler().postDelayed({
            val intent = Intent(this@ThankYouActivity, ResultActivity::class.java)
            startActivity(intent)
            finish() // Prevent going back to the intro screen
        }, 4000) // 4000ms = 4 seconds
    }
}
