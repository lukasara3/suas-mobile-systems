package com.example.tutorial5_ex1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

data class MyItem(val title: String, val subtitle: String)
class MainActivity : AppCompatActivity() {

    private val list = listOf(
        MyItem("Vingadores", "Fase 1 - 2012"),
        MyItem("Guerra Civil", "Capitão América 3"),
        MyItem("Homem Aranha", "Peter Parker"),
        MyItem("X-Men", "Os Mutantes da Fox"),
        MyItem("Capitão América e o Soldado Invernal", "Os Irmãos Russo"),
        MyItem("Homem de Ferro", "O início de tudo (2008)"),
        MyItem("Black Panther", "Wakanda Forever"),
        MyItem("Thor", "O Deus do Trovão")
    )

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        val arrayAdapter = object : ArrayAdapter<MyItem>(
            this,
            android.R.layout.simple_list_item_2,
            android.R.id.text1,
            list
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                val text1 = view.findViewById<TextView>(android.R.id.text1)
                val text2 = view.findViewById<TextView>(android.R.id.text2)

                val item = getItem(position)

                text1.text = item?.title
                text2.text = item?.subtitle

                return view
            }
        }

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = list[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("TITLE", selectedItem.title)
            intent.putExtra("SUBTITLE",selectedItem.subtitle)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}