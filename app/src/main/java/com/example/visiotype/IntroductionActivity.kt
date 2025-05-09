package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class IntroductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        // Generate a random document ID
        val randomDocId = UUID.randomUUID().toString()

        // Set the XML layout for the introduction screen
        setContentView(R.layout.activity_introduction)

        // After a delay of 4 seconds, start the AgeGroupActivity
        Handler().postDelayed({
            val intent = Intent(this@IntroductionActivity, ConsentFormActivity::class.java)
            intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
            startActivity(intent)
            finish() // Prevent going back to the intro screen
        }, 4000) // 4000ms = 4 seconds
    }
}
