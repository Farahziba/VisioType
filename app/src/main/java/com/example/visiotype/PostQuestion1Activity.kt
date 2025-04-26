package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class PostQuestion1Activity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question_1)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")
        val finalType = intent.getStringExtra("FINAL_RESULT")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val postQ1Options = findViewById<RadioGroup>(R.id.postQ1Options)
        val nextButton = findViewById<Button>(R.id.nextBtnQ1)

        nextButton.setOnClickListener {
            // Check if a radio button is selected
            val selectedOptionId = postQ1Options.checkedRadioButtonId
            if (selectedOptionId != -1) {  // If a radio button is selected
                val selectedOption = findViewById<RadioButton>(selectedOptionId)?.text.toString()

                // Save the selected option to Firestore
                if (randomDocId != null) {
                    saveQ1ToFirestore(selectedOption, randomDocId)
                }

                // Navigate to the next activity
                val intent = Intent(this, PostQuestion2Activity::class.java)
                intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
                intent.putExtra("FINAL_RESULT", finalType)
                startActivity(intent)
            } else {
                // Show a message to inform the user to select an option
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveQ1ToFirestore(q1: String, id: String) {
        val userId = auth.currentUser?.uid ?: id
        val data = mapOf("post_quest_1" to q1)

        val userDocRef = firestore.collection("users").document(userId)

        // Use merge to add data without overwriting other fields
        userDocRef.set(data, SetOptions.merge())
            .addOnSuccessListener {
                // Successfully saved
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
