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
import com.google.firebase.firestore.SetOptions

class EducationQuestionActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val educationOptions = findViewById<RadioGroup>(R.id.educationOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnEducation)

        nextButton.setOnClickListener {
            // Check if a radio button is selected
            val selectedEducationId = educationOptions.checkedRadioButtonId
            if (selectedEducationId != -1) {  // If a radio button is selected
                val selectedEducation = findViewById<RadioButton>(selectedEducationId)?.text.toString()

                // Save the education level to Firestore
                if (randomDocId != null) {
                    saveEducationToFirestore(selectedEducation, randomDocId)
                }

                // Navigate to the WelcomeActivity
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
                startActivity(intent)
            } else {
                // Show a message to inform the user to select an education level
                Toast.makeText(this, "Please select your education level", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveEducationToFirestore(education: String, id: String) {
        val userId = auth.currentUser?.uid ?: id
        val educationData = mapOf("education" to education)

        val userDocRef = firestore.collection("users").document(userId)

        // Update the user document by merging the education field
        userDocRef.set(educationData, SetOptions.merge())
            .addOnSuccessListener {
                // Education saved successfully
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
