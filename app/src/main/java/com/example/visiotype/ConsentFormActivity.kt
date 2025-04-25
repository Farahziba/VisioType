package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class ConsentFormActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance() // Firebase Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.consent_form)

        val checkBox = findViewById<CheckBox>(R.id.consentCheckBox)
        val agreeButton = findViewById<Button>(R.id.agreeButton)
        val declineButton = findViewById<Button>(R.id.declineButton)

        FirebaseApp.initializeApp(this)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        // Enable agree button only when checkbox is checked
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            agreeButton.isEnabled = isChecked
        }

        // Handle Agree button click
        agreeButton.setOnClickListener {
            // Store the consent data in Firestore
            randomDocId?.let { it1 -> storeConsentData("agreed", it1) }

            // Start the next activity
            val intent = Intent(this, AgeQuestionActivity::class.java)
            intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
            startActivity(intent)
            finish()
        }

        // Handle Decline button click
        declineButton.setOnClickListener {
            // Store the consent data in Firestore
            randomDocId?.let { it1 -> storeConsentData("declined", it1) }

            // Exit the app
            finishAffinity()
        }
    }

    // Function to store the consent data in Firestore
    private fun storeConsentData(consentStatus: String, id: String) {
        val userId = id// You can replace this with the actual user ID if available
        val consentData = hashMapOf(
            "consent_status" to consentStatus,
            "timestamp" to System.currentTimeMillis()
        )

        // Save the consent data in the 'users' collection
        firestore.collection("users")
            .document(userId)
            .set(consentData)
            .addOnSuccessListener {
                // Successfully saved to Firestore
            }
            .addOnFailureListener { e ->
                // Handle the error
                e.printStackTrace()
            }
    }
}
