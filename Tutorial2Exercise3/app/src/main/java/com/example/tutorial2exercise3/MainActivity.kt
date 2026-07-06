package com.example.tutorial2exercise3

import android.hardware.biometrics.PromptContentItemPlainText
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var playerName: EditText
    private lateinit var startButton: Button
    private lateinit var falseButton: Button
    private lateinit var trueButton: Button
    private lateinit var question: TextView
    private lateinit var result: TextView

    private lateinit var catalog: Catalog

    var score: Int = 0

    fun showNextStatement(){
        catalog.jumpToNextStatement()
        val nextStatement = catalog.getCurrentStatement()
        if (nextStatement != null){
            question.text = nextStatement.text
        }
        else{
            endGame()
        }
    }

    fun endGame(){
        question.visibility = TextView.INVISIBLE
        falseButton.visibility = Button.INVISIBLE
        trueButton.visibility = Button.INVISIBLE

        val name = playerName.text.toString()
        result.text = "Wow $name ! You got $score points!"
        result.visibility = TextView.VISIBLE
    }

    fun checkAnswer(userAnswer: Boolean){
        val currentStatement = catalog.getCurrentStatement()

        if (currentStatement != null) {
            if (userAnswer == currentStatement.answer) {
                score += 100
            } else {
                score -= 100
            }
        }

        showNextStatement()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        playerName = findViewById(R.id.playerNameInput)

        startButton = findViewById(R.id.startButton)
        startButton.isEnabled = false

        falseButton = findViewById(R.id.falseButton)
        trueButton = findViewById(R.id.trueButton)
        question = findViewById(R.id.question)
        result = findViewById(R.id.resultText)

        catalog = Catalog()

        playerName.doOnTextChanged { text, start, before, count ->
            startButton.isEnabled = !text.isNullOrBlank()  //nao precisa de um IF pq o !text.isNullOrBlank() ja faz o que o if faria, que ia inverter (para text null TRUE, enabled eh FALSE) e vice versa.
        }

        startButton.setOnClickListener {
            playerName.visibility = EditText.INVISIBLE
            startButton.visibility = Button.INVISIBLE
            question.visibility = TextView.VISIBLE
            question.text = catalog.getCurrentStatement()?.text ?:""
            falseButton.visibility = Button.VISIBLE
            trueButton.visibility = Button.VISIBLE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}