package com.example.tutorial5_ex1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private val list = listOf(
        "Vingadores",
        "Guerra Civil",
        "Homem Aranha",
        "X-Men",
        "Capitão América e o Soldado Invernal",
        "Homem de Ferro",
        "Black Panther",
        "Thor"
    )

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView = findViewById(R.id.listView)
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val title = list[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("TITLE", title)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}