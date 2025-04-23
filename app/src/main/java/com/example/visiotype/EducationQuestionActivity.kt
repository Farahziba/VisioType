package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class EducationQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education)

        val educationOptions = findViewById<RadioGroup>(R.id.educationOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnEducation)

        nextButton.setOnClickListener {
            // Get the selected education option
            val selectedEducation = findViewById<RadioButton>(educationOptions.checkedRadioButtonId)?.text.toString()

            // Optionally, store the selected education in SharedPreferences or a database.

            // You can move to the next activity or show the result here
            // For now, just log the data or show a message
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}
