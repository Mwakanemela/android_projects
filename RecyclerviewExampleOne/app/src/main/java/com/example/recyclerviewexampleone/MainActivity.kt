package com.example.recyclerviewexampleone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var imageId:Array<Int>
    private lateinit var names:Array<String>
    private lateinit var description:Array<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Software>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       imageId = arrayOf(
           R.drawable.image1,
           R.drawable.image2,
           R.drawable.image3,
           R.drawable.image4,
           R.drawable.image5,
       )
       names = arrayOf(
           "sw1",
           "sw2",
           "sw3",
           "sw4",
           "sw5",
       )

       description = arrayOf(
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
       )

       recyclerView = findViewById(R.id.myRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        itemArrayList = arrayListOf()
       getData()

        recyclerView.adapter = RecyclerViewAdapter(itemArrayList)
    }

    private fun getData() {

        for(i in names.indices) {
            val software = Software(imageId[i], names[i], description[i])
            itemArrayList.add(software)
        }
    }
}