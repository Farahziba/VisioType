package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val startQuizButton = findViewById<Button>(R.id.startQuizButton)
        startQuizButton.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, QuizQuestionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
