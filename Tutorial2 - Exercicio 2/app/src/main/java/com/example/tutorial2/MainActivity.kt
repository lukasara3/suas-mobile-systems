package com.example.tutorial2

import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var image: ImageView
    private lateinit var ratingBar: RatingBar
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton

    private var catRating: Float = 0.0f
    private var dogRating: Float = 0.0f

    private fun evaluate(){
        val message: String

        if(radioButton1.isChecked){
            message = "You rated the dog with $dogRating stars!"
        }
        else{
            message = "You rated the cat with $catRating stars!"
        }

        Snackbar.make(button, message, Snackbar.LENGTH_SHORT).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        image = findViewById(R.id.imageView)
        ratingBar = findViewById(R.id.ratingBar)
        radioGroup = findViewById(R.id.radioGroup)
        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)

        button.setOnClickListener {
            evaluate()
        }

        radioButton1.setOnClickListener {
            image.setImageResource(R.drawable.dog)
            ratingBar.rating = dogRating
        }

        radioButton2.setOnClickListener {
            image.setImageResource(R.drawable.cat)
            ratingBar.rating = catRating
        }

        ratingBar.setOnRatingBarChangeListener{ _, rating, fromUser ->
            if (fromUser){
                if (radioButton1.isChecked){
                    dogRating = rating
                }
                else if(radioButton2.isChecked){
                    catRating = rating
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}