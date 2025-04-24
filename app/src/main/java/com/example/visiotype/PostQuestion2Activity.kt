package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class PostQuestion2Activity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question_2)

        // Retrieve the random document ID passed from the first activity
        val randomDocId = intent.getStringExtra("DOCUMENT_ID")
        val finalType = intent.getStringExtra("FINAL_RESULT")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val answerInput = findViewById<EditText>(R.id.textAnswer)
        val submitBtn = findViewById<Button>(R.id.nextBtnQ2)

        submitBtn.setOnClickListener {
            val answer = answerInput.text.toString().trim()

            if (answer.isEmpty()) {
                Toast.makeText(this, "Please enter your answer.", Toast.LENGTH_SHORT).show()
            } else {

                // Save the selected age to Firestore
                if (randomDocId != null) {
                    saveQ1ToFirestore(answer, randomDocId)
                }

                // Navigate to the GenderQuestionActivity
                val intent = Intent(this, ThankYouActivity::class.java)
                intent.putExtra("DOCUMENT_ID", randomDocId)  // Passing the document ID
                intent.putExtra("FINAL_RESULT", finalType)
                startActivity(intent)
            }
        }
    }

    private fun saveQ1ToFirestore(q2: String, id: String) {
        val userId = auth.currentUser?.uid ?: id
        val ageData = mapOf("post_quest_2" to q2)

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
