package com.example.recyclerviewexampleone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewexampleone.nestedrv.ChildDataClass
import com.example.recyclerviewexampleone.nestedrv.ParentAdapter
import com.example.recyclerviewexampleone.nestedrv.ParentDataClass
import java.util.Locale

class NestedRecyclerViewActivity : AppCompatActivity() {

    val parentItemsList = ArrayList<ParentDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)

        val rvParent = findViewById<RecyclerView>(R.id.rv_parent)
        rvParent.setHasFixedSize(true)
        rvParent.layoutManager = LinearLayoutManager(this)
        val adapter = ParentAdapter(parentItemsList)

        val searchView = findViewById<SearchView>(R.id.searchView)
        setData()

        rvParent.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val searchList = ArrayList<ParentDataClass>()


                if(newText != null) {
                    for(i in parentItemsList) {
                        if(i.title.lowercase(Locale.ROOT).contains(newText)) {
                            searchList.add(i)
                        }
                    }
                    if(searchList.isEmpty()) {
                        Toast.makeText(this@NestedRecyclerViewActivity, "No data", Toast.LENGTH_LONG).show()
                    }else {
                        adapter.applySearch(searchList )
                    }
                }
                return true
            }

        })
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