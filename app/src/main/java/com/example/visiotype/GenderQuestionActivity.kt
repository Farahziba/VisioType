package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class GenderQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        val genderOptions = findViewById<RadioGroup>(R.id.genderOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnGender)

        nextButton.setOnClickListener {
            // Get the selected gender option
            val selectedGender = findViewById<RadioButton>(genderOptions.checkedRadioButtonId)?.text.toString()

            // Optionally, store the selected gender in SharedPreferences or a database.

            // Navigate to the EducationQuestionActivity
            val intent = Intent(this, EducationQuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
