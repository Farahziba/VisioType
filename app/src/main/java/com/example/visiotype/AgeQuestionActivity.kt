package com.example.visiotype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class AgeQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_group)

        val ageOptions = findViewById<RadioGroup>(R.id.ageOptions)
        val nextButton = findViewById<Button>(R.id.nextBtnAge)

        nextButton.setOnClickListener {
            // Get the selected age option
            val selectedAge = findViewById<RadioButton>(ageOptions.checkedRadioButtonId)?.text.toString()

            // Optionally, store the selected age in SharedPreferences or a database.

            // Navigate to the GenderQuestionActivity
            val intent = Intent(this, GenderQuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
