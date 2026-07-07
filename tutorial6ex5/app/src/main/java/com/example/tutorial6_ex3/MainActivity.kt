package com.example.tutorial6_ex3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var enterLocation: EditText
    private lateinit var showMapsButton: Button
    private var timer: CountDownTimer? = null
    private lateinit var galleryButton: Button
    private lateinit var imageView: ImageView

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK){
            val data = result.data
            val imageUri = data?.data

            if (imageUri != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    private fun openGallery(){

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        galleryLauncher.launch(intent)
    }

    private fun showLocation(location:String){

        val queryUrl = "geo:0,0?q=$location"
        val uri = queryUrl.toUri()
        val intent = Intent(Intent.ACTION_VIEW,uri)

        startActivity(intent)
    }

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
        progressBar = findViewById(R.id.progressBar)

        enterLocation = findViewById(R.id.editTextText)
        showMapsButton = findViewById(R.id.button)

        showMapsButton.setOnClickListener {
            val location = enterLocation.text.toString()
            showLocation(location)
        }

        galleryButton = findViewById(R.id.button_gallery)
        galleryButton.setOnClickListener {
            openGallery()
        }
        imageView = findViewById(R.id.imageView)

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

            R.id.itemSave -> {
                val sharedPref = getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("LAST_LOCATION", enterLocation.text.toString())
                editor.apply()

                Snackbar.make(enterLocation, "Localização guardada!", Snackbar.LENGTH_SHORT).show()
                true
            }

            R.id.itemLoad -> {
                val sharedPref = getPreferences(Context.MODE_PRIVATE)

                val savedText = sharedPref.getString("LAST_LOCATION", "")

                enterLocation.setText(savedText)

                Snackbar.make(enterLocation, "Localização carregada!", Snackbar.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}