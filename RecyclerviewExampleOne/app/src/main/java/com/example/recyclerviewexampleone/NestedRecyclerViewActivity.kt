package com.example.recyclerviewexampleone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexampleone.nestedrv.ChildDataClass
import com.example.recyclerviewexampleone.nestedrv.ParentAdapter
import com.example.recyclerviewexampleone.nestedrv.ParentDataClass

class NestedRecyclerViewActivity : AppCompatActivity() {

    val parentItemsList = ArrayList<ParentDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)

        val rvParent = findViewById<RecyclerView>(R.id.rv_parent)
        rvParent.setHasFixedSize(true)
        rvParent.layoutManager = LinearLayoutManager(this)
        val adapter = ParentAdapter(parentItemsList)

        setData()

        rvParent.adapter = adapter
    }

    private fun setData() {
        val childItemsList = ArrayList<ChildDataClass>()

        childItemsList.add(ChildDataClass(R.drawable.image1))
        childItemsList.add(ChildDataClass(R.drawable.image2))
        childItemsList.add(ChildDataClass(R.drawable.image3))
        childItemsList.add(ChildDataClass(R.drawable.image4))
        childItemsList.add(ChildDataClass(R.drawable.image5))
        childItemsList.add(ChildDataClass(R.drawable.image1))


        parentItemsList.add(ParentDataClass("Software", childItemsList))

        val childItemsList2 = ArrayList<ChildDataClass>()

        childItemsList2.add(ChildDataClass(R.drawable.image5))
        childItemsList2.add(ChildDataClass(R.drawable.image4))
        childItemsList2.add(ChildDataClass(R.drawable.image2))
        childItemsList2.add(ChildDataClass(R.drawable.image1))
        childItemsList2.add(ChildDataClass(R.drawable.image2))
        childItemsList2.add(ChildDataClass(R.drawable.image1))


        parentItemsList.add(ParentDataClass("Software Engineer", childItemsList2))
    }
}