package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PostQuestion2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question_2)

        val answerInput = findViewById<EditText>(R.id.textAnswer)
        val submitBtn = findViewById<Button>(R.id.nextBtnQ2)

        submitBtn.setOnClickListener {
            val answer = answerInput.text.toString().trim()

            if (answer.isEmpty()) {
                Toast.makeText(this, "Please enter your answer.", Toast.LENGTH_SHORT).show()
            } else {
                // Save the answer or pass it to the next activity
                // Example: Intent to result or summary page
                val intent = Intent(this, ThankYouActivity::class.java)
                intent.putExtra("freeTextAnswer", answer)
                startActivity(intent)
                finish()
            }
        }
    }
}
