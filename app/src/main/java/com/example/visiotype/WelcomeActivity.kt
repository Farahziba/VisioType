package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        val startQuizButton = findViewById<Button>(R.id.startQuizButton)
        startQuizButton.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, QuizQuestionActivity::class.java)
            intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
            startActivity(intent)
            finish()
        }
    }
}
