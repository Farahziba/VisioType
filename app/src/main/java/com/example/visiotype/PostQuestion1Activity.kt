package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            val selectedOption = findViewById<RadioButton>(postQ1Options.checkedRadioButtonId)?.text.toString()

            // Save the selected age to Firestore
            if (randomDocId != null) {
                saveQ1ToFirestore(selectedOption, randomDocId)
            }

            // Navigate to the GenderQuestionActivity
            val intent = Intent(this, PostQuestion2Activity::class.java)
            intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
            intent.putExtra("FINAL_RESULT", finalType)
            startActivity(intent)
        }
    }

    private fun saveQ1ToFirestore(q1: String, id: String) {
        val userId = auth.currentUser?.uid ?: id
        val ageData = mapOf("post_quest_1" to q1)

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
