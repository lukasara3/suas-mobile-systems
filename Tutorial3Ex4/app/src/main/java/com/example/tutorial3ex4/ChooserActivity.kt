package com.example.tutorial3ex4

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent


class ChooserActivity : AppCompatActivity() {

    private lateinit var imageButton1: ImageButton
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton

    private fun chooseImage(imageId: Int){
        val intent = Intent()
        intent.putExtra("IMAGE_ID", imageId)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chooser)

        imageButton1 = findViewById(R.id.imageButton)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)

        imageButton1.setOnClickListener { chooseImage(R.drawable.cat)}
        imageButton2.setOnClickListener { chooseImage(R.drawable.dog)}
        imageButton3.setOnClickListener { chooseImage(R.drawable.giraffe)}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}