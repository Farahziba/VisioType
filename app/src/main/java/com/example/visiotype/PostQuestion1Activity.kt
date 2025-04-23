package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class PostQuestion1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question_1)

        val postQ1Options = findViewById<RadioGroup>(R.id.postQ1Options)
        val nextButton = findViewById<Button>(R.id.nextBtnQ1)

        nextButton.setOnClickListener {
            // Get the selected education option
            val selectedEducation = findViewById<RadioButton>(postQ1Options.checkedRadioButtonId)?.text.toString()

            // Optionally, store the selected education in SharedPreferences or a database.

            // You can move to the next activity or show the result here
            // For now, just log the data or show a message
            val intent = Intent(this, PostQuestion2Activity::class.java)
            startActivity(intent)
        }
    }
}
