package com.example.recyclerviewexampleone


import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT
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
                //RecyclerViewAdapter(itemArrayList).deleteItem(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition

                // Remove the item from the ArrayList
                itemArrayList.removeAt(position)

                // Notify the adapter of the item removal
                recyclerView.adapter?.notifyItemRemoved(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.my_background
                        )
                    )
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .create()
                    .decorate()
            }
//            override fun onChildDraw(
//                c: Canvas?,
//                recyclerView: RecyclerView?,
//                viewHolder: RecyclerView.ViewHolder?,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                RecyclerViewSwipeDecorator.Builder(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                    .addBackgroundColor(
//                        ContextCompat.getColor(
//                            this@MainActivity,
//                            R.color.my_background
//                        )
//                    )
//                    .addActionIcon(R.drawable.baseline_delete_24)
//                    .create()
//                    .decorate()
//                super.onChildDraw(
//                    c!!, recyclerView!!,
//                    viewHolder!!, dX, dY, actionState, isCurrentlyActive
//                )
//            }


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