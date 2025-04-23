package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var questionImage: ImageView
    private lateinit var questionText: TextView
    private lateinit var option1: Button
    private lateinit var option2: Button

    private var currentQuestionIndex = 0

    private val questions = listOf(
        Triple(R.drawable.q1, "What do you see first in this image?", listOf("The path", "The trees")),
        Triple(R.drawable.q2, "What do you see first in this image?", listOf("The man", "The woman")),
        Triple(R.drawable.q3, "What do you see first in this image?", listOf("A face", "The clouds")),
        Triple(R.drawable.q4, "What do you see first in this image?", listOf("The person", "The tiger")),
        Triple(R.drawable.q5, "What do you see first in this image?", listOf("The horse", "The mountains")),
        Triple(R.drawable.q6, "What do you see first in this image?", listOf("A face", "The woman")),
        Triple(R.drawable.q7, "What do you see first in this image?", listOf("The elephant", "The patterns")),
        Triple(R.drawable.q8, "What do you see first in this image?", listOf("The butterfly", "The face")),
        Triple(R.drawable.q9, "What do you see first in this image?", listOf("The beach", "The lighthouse")),
        Triple(R.drawable.q10, "What do you see first in this image?", listOf("The glass of water", "The sunset")),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        questionImage = findViewById(R.id.questionImage)
        questionText = findViewById(R.id.questionText)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)

        showQuestion()

        option1.setOnClickListener {
            handleAnswer()
        }

        option2.setOnClickListener {
            handleAnswer()
        }
    }

    private fun showQuestion() {
        val (imageRes, question, options) = questions[currentQuestionIndex]
        questionImage.setImageResource(imageRes)
        questionText.text = question
        option1.text = options[0]
        option2.text = options[1]
    }

    private fun handleAnswer() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            showQuestion()
        } else {
            startActivity(Intent(this, PostGameActivity::class.java)) // Or any end page
            finish()
        }
    }
}
