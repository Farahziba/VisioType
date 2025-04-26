package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var questionImage: ImageView
    private lateinit var questionText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var currentQuestionIndex = 0

    private var explorerScore = 0
    private var thinkerScore = 0
    private var leaderScore = 0
    private var dreamerScore = 0

    private val questions = listOf(
        Triple(R.drawable.q1, "What do you see first in this image?", listOf("The path", "The trees")),
        Triple(R.drawable.q2, "What do you see first in this image?", listOf("The man", "The woman")),
        Triple(R.drawable.q3, "What do you see first in this image?", listOf("A face", "The clouds")),
        Triple(R.drawable.q4, "What do you see first in this image?", listOf("The person", "The tiger")),
        Triple(R.drawable.q5, "What do you see first in this image?", listOf("The horse", "The mountains")),
        Triple(R.drawable.q6, "What do you see first in this image?", listOf("A face", "A tree")),
        Triple(R.drawable.q7, "What do you see first in this image?", listOf("The elephant", "The patterns")),
        Triple(R.drawable.q8, "What do you see first in this image?", listOf("The butterfly", "The face")),
        Triple(R.drawable.q9, "What do you see first in this image?", listOf("The beach", "The lighthouse")),
        Triple(R.drawable.q10, "What do you see first in this image?", listOf("The glass of water", "The sunset")),
    )

    private var hasNotificationShown = false  // Flag to track if the notification has been shown
    private var notificationStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        val randomDocId = intent.getStringExtra("DOCUMENT_ID")

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        questionImage = findViewById(R.id.questionImage)
        questionText = findViewById(R.id.questionText)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)

        showQuestion()
        val randomIndex = (6..8).random()  // Random number between 6 and 8

        option1.setOnClickListener {
            randomDocId?.let { handleAnswer(1, it) }
            if (currentQuestionIndex == randomIndex && !hasNotificationShown) {
                randomDocId?.let { it1 -> showNotification(it1) }
                hasNotificationShown = true  // Set the flag to true after showing the notification
            }
        }

        option2.setOnClickListener {
            randomDocId?.let { handleAnswer(2, it) }
            if (currentQuestionIndex == randomIndex && !hasNotificationShown) {
                randomDocId?.let { it1 -> showNotification(it1) }
                hasNotificationShown = true  // Set the flag to true after showing the notification
            }
        }
    }

    private fun showQuestion() {
        val (imageRes, question, options) = questions[currentQuestionIndex]
        questionImage.setImageResource(imageRes)
        questionText.text = question
        option1.text = options[0]
        option2.text = options[1]
    }

    private fun handleAnswer(optionSelected: Int, docId: String) {
        when (currentQuestionIndex) {
            0, 4 -> if (optionSelected == 1) explorerScore++ else thinkerScore++
            1 -> if (optionSelected == 1) leaderScore++ else dreamerScore++
            2 -> if (optionSelected == 1) dreamerScore++ else thinkerScore++
            3 -> if (optionSelected == 1) thinkerScore++ else leaderScore++
            5 -> if (optionSelected == 1) dreamerScore++ else thinkerScore++
            6 -> if (optionSelected == 1) explorerScore++ else dreamerScore++
            7 -> if (optionSelected == 1) dreamerScore++ else thinkerScore++
            8 -> if (optionSelected == 1) explorerScore++ else leaderScore++
            9 -> if (optionSelected == 1) thinkerScore++ else dreamerScore++
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            val finalType = determineResultType()
            saveResultToFirestore(docId, finalType)

            val intent = Intent(this, PostGameActivity::class.java)
            intent.putExtra("DOCUMENT_ID", docId)
            intent.putExtra("FINAL_RESULT", finalType)
            startActivity(intent)
            finish()
        }
    }

    private fun determineResultType(): String {
        val scores = mapOf(
            "Explorer" to explorerScore,
            "Thinker" to thinkerScore,
            "Leader" to leaderScore,
            "Dreamer" to dreamerScore
        )
        return scores.maxByOrNull { it.value }?.key ?: "Unknown"
    }

    private fun saveResultToFirestore(id: String, finalType: String) {
        val userId = auth.currentUser?.uid ?: id

        val resultData = hashMapOf(
            "explorerScore" to explorerScore,
            "thinkerScore" to thinkerScore,
            "leaderScore" to leaderScore,
            "dreamerScore" to dreamerScore,
            "finalResult" to finalType
        )

        val userDocRef = firestore.collection("users").document(userId)
        userDocRef.set(resultData, com.google.firebase.firestore.SetOptions.merge())
            .addOnSuccessListener {
                // Result saved
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun showNotification(id: String) {
        notificationStartTime = System.currentTimeMillis()
        val notificationDialog = CustomNotificationDialogFragment(1,id)
        notificationDialog.setOnNotificationInteractionListener(object : CustomNotificationDialogFragment.NotificationInteractionListener {
            override fun onNotificationClick() {
                val responseTime = System.currentTimeMillis() - notificationStartTime
                logUserInteraction("notification_click", "security_warning", id, responseTime)
            }

            override fun onNotificationDismiss() {
                logUserInteraction("notification_dismissed", "security_warning", id)
            }
        })

        notificationDialog.show(supportFragmentManager, "FirstWarningDialog")
        logUserInteraction("notification_shown", "security_warning", id)
    }

    private fun logUserInteraction(action: String, notificationType: String, id: String, responseTime: Long? = null) {
        val userId = auth.currentUser?.uid ?: id  // Use 'guest' if no user is logged in
        val actionData = hashMapOf<String, Any>(
            "action" to action,
            "timestamp" to System.currentTimeMillis(),
            "notification_type" to notificationType
        )

        responseTime?.let {
            actionData["response_time"] = it
        }

        firestore.collection("user_interactions")
            .document(userId)
            .collection("warnings")
            .document(notificationType)
            .set(actionData)
            .addOnSuccessListener {
                // Handle success
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
