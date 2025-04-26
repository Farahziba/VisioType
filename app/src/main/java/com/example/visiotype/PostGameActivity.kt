package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class PostGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the XML layout for the introduction screen
        setContentView(R.layout.activity_post_game)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")
        val finalType = intent.getStringExtra("FINAL_RESULT")

        // After a delay of 4 seconds, start the AgeGroupActivity
        Handler().postDelayed({
            val intent = Intent(this@PostGameActivity, PostQuestion1Activity::class.java)
            intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
            intent.putExtra("FINAL_RESULT", finalType)
            startActivity(intent)
            finish() // Prevent going back to the intro screen
        }, 4000) // 4000ms = 4 seconds
    }
}
