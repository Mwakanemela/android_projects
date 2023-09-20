package com.example.recyclerviewexampleone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

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
           R.drawable.image1,
           R.drawable.image3,
           R.drawable.image4,
           R.drawable.image1,
           R.drawable.image2,
           R.drawable.image3,

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
           "sw5",
           "sw3",
           "sw4",
           "sw1",
           "sw2",
           "sw3",

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
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",

           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
           "Software Engineer Programmer",
       )

       recyclerView = findViewById(R.id.myRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this,  RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        itemArrayList = arrayListOf()
       getData()


        recyclerView.adapter = RecyclerViewAdapter(itemArrayList)

        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                source: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = source.adapterPosition
                val targetPosition = target.adapterPosition

                Collections.swap(itemArrayList,sourcePosition, targetPosition)

                recyclerView.adapter?.notifyItemMoved(sourcePosition, targetPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }

        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getData() {

        for(i in names.indices) {
            val software = Software(imageId[i], names[i], description[i])
            itemArrayList.add(software)
        }
    }
}