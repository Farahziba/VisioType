package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class GenderQuestionActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val genderOptions = findViewById<RadioGroup>(R.id.genderOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnGender)

        nextButton.setOnClickListener {
            // Check if a radio button is selected
            val selectedGenderId = genderOptions.checkedRadioButtonId
            if (selectedGenderId != -1) {  // If a radio button is selected
                val selectedGender = findViewById<RadioButton>(selectedGenderId)?.text.toString()

                // Save gender to Firestore
                if (randomDocId != null) {
                    saveGenderToFirestore(selectedGender, randomDocId)
                }

                // Move to next activity
                val intent = Intent(this, EducationQuestionActivity::class.java)
                intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
                startActivity(intent)
            } else {
                // Show a message to inform the user to select a gender
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveGenderToFirestore(gender: String, id: String) {
        val userId = auth.currentUser?.uid ?: id

        val genderData = mapOf("gender" to gender)

        val userDocRef = firestore.collection("users").document(userId)

        // Update the user document by merging gender field
        userDocRef.set(genderData, com.google.firebase.firestore.SetOptions.merge())
            .addOnSuccessListener {
                // Gender saved successfully
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
