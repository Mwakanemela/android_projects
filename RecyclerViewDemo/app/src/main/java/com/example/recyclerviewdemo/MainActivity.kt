package com.example.recyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val fruitsList = listOf<Fruit>(
        Fruit("Apple", "Hava"),
        Fruit("Banana", "Monkey"),
        Fruit("Orange", "Frank"),
        Fruit("Guava", "Alaba" ),
        Fruit("Lemon", "Don")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.recycler_view_background))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecyclerViewAdapter(fruitsList) { selectedItem: Fruit ->
            listItemClicked(selectedItem)
        }
    }

    private  fun listItemClicked(fruit: Fruit) {
        Toast.makeText(
            this@MainActivity,
            "Supplier is : ${fruit.supplire}", Toast.LENGTH_SHORT
        ).show()
    }
}