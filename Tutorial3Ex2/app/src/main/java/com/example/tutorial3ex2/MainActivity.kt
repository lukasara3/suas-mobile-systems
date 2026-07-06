package com.example.tutorial3ex2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {

    private lateinit var number1: EditText
    private lateinit var number2: EditText
    private lateinit var multiplyButton: Button
    private lateinit var radioGroup: RadioGroup

    fun getMode(): CustomData.Mode{
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        return when (radioGroup.checkedRadioButtonId) {
            R.id.radioButton -> CustomData.Mode.ADD
            R.id.radioButton2 -> CustomData.Mode.SUBTRACT
            R.id.radioButton3 -> CustomData.Mode.MULTIPLY
            else -> CustomData.Mode.ADD
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        number1 = findViewById(R.id.editTextNumber)
        number2 = findViewById(R.id.editTextNumber2)
        multiplyButton = findViewById(R.id.button)

        multiplyButton.isEnabled = false

        number1.doOnTextChanged { _, _, _, _ ->
            multiplyButton.isEnabled = !number1.text.isNullOrBlank() && !number2.text.isNullOrBlank()
        }

        number2.doOnTextChanged { _, _, _, _ ->
            multiplyButton.isEnabled = !number1.text.isNullOrBlank() && !number2.text.isNullOrBlank()
        }

        multiplyButton.setOnClickListener {
            val val1 = number1.text.toString().toInt()
            val val2 = number2.text.toString().toInt()
            val customData = CustomData(val1,val2,getMode())

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("CUSTOM DATA", customData)

            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}