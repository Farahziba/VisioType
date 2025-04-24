package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val finalType = intent.getStringExtra("FINAL_RESULT")

        val resultImageView = findViewById<ImageView>(R.id.resultImageView)
        val resultDescription = findViewById<TextView>(R.id.resultDescription)

        when (finalType) {
            "Explorer" -> {
                resultImageView.setImageResource(R.drawable.explorer)
                resultDescription.text = "\tThe Explorer:\n" + "You're a free spirit who thrives on adventure. You’re curious, always seeking new experiences, and you love to step into the unknown. Your personality thrives when you're given freedom and a chance to explore your surroundings.\n"
            }
            "Thinker" -> {
                resultImageView.setImageResource(R.drawable.thinker)
                resultDescription.text = "\tThe Thinker:\n" + "You're the thoughtful one. You love analyzing, reflecting, and figuring out the deeper meanings of life. You seek knowledge, understanding, and stability in both yourself and the world around you.\n"
            }
            "Leader" -> {
                resultImageView.setImageResource(R.drawable.leader)
                resultDescription.text = "\tThe Leader:\n" + "You’re a natural-born leader. You are determined, confident, and capable of taking charge in any situation. People look to you for guidance, and you know how to inspire others to follow.\n"
            }
            "Dreamer" -> {
                resultImageView.setImageResource(R.drawable.dreamer)
                resultDescription.text = "\tThe Dreamer:\n" + "You're a imaginative, creative, intuitive. You have a vivid imagination and a deep emotional connection to the world. Creativity and introspection are your strong suits.\n"
            }
            else -> {
                resultImageView.setImageResource(R.drawable.none)
                resultDescription.text = "We couldn’t determine your personality type. Try again!"
            }
        }

        val startQuizButton = findViewById<Button>(R.id.startQuizButton)
        startQuizButton.setOnClickListener {
            val intent = Intent(this@ResultActivity, IntroductionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
