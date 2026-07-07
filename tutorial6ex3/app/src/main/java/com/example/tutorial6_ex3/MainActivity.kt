package com.example.tutorial6_ex3

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private var timer: CountDownTimer? = null

    private fun createTimeTask(){
        timer?.cancel()
        progressBar.progress = 0

        timer = object : CountDownTimer(8000,80){
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress += 1
            }

            override fun onFinish() {
                progressBar.progress = 100
                Snackbar.make(progressBar, "A tarefa terminou!", Snackbar.LENGTH_SHORT).show()
            }
        }

        timer?.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){
            R.id.itemDo -> {
                createTimeTask()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}