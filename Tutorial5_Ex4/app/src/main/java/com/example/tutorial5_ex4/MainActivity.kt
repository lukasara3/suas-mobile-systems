package com.example.tutorial5_ex4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

data class Item(val name: String, val resID: Int)
class CustomAdapter(
    private val data: List<Item>,
    private val listener: CustomItemClickListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    fun interface CustomItemClickListener {
        fun onItemClick(item: Item)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.textViewName.text = currentItem.name
        holder.imageView.setImageResource(currentItem.resID)
        holder.itemView.setOnClickListener {
            listener.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int = data.size
}
class MainActivity : AppCompatActivity() {

    fun createData(): List<Item>{
        val list = listOf<Item>(
            Item("Mariana", R.drawable.linda),
            Item("Cr7", R.drawable.cr7),
            Item("BarbaBOL", R.drawable.barba),
            Item("Joe", R.drawable.joe)
        )

        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val myList = createData()
        val adapter = CustomAdapter(myList) { item ->
            Snackbar.make(recyclerView, "You click on: ${item.name}", Snackbar.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}