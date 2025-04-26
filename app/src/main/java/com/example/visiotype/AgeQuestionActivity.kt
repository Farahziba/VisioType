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

class AgeQuestionActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_group)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val ageOptions = findViewById<RadioGroup>(R.id.ageOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnAge)

        nextButton.setOnClickListener {
            // Check if a radio button is selected
            val selectedAgeId = ageOptions.checkedRadioButtonId
            if (selectedAgeId != -1) {  // If a radio button is selected
                val selectedAge = findViewById<RadioButton>(selectedAgeId)?.text.toString()

                // Save the selected age to Firestore
                if (randomDocId != null) {
                    saveAgeToFirestore(selectedAge, randomDocId)
                }

                // Navigate to the GenderQuestionActivity
                val intent = Intent(this, GenderQuestionActivity::class.java)
                intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
                startActivity(intent)
            } else {
                // Show a message to inform the user to select an age group
                Toast.makeText(this, "Please select your age group", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveAgeToFirestore(age: String, id: String) {
        val userId = auth.currentUser?.uid ?: id
        val ageData = mapOf("age_group" to age)

        val userDocRef = firestore.collection("users").document(userId)

        // Use merge to add age without overwriting other fields
        userDocRef.set(ageData, SetOptions.merge())
            .addOnSuccessListener {
                // Successfully saved
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
