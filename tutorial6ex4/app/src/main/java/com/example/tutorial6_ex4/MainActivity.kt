package com.example.tutorial6_ex4

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var enterLocation: EditText
    private lateinit var showMapsButton: Button

    private fun showLocation(location:String){

        val queryUrl = "geo:0,0?q=$location"
        val uri = queryUrl.toUri()
        val intent = Intent(Intent.ACTION_VIEW,uri)

        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        enterLocation = findViewById(R.id.editTextText)
        showMapsButton = findViewById(R.id.button)

        showMapsButton.setOnClickListener {
            val location = enterLocation.text.toString()
            showLocation(location)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}