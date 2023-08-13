package com.example.viewmodellivedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private  lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countText = findViewById<TextView>(R.id.tvCount)
        val countBtn = findViewById<Button>(R.id.countBtn)
        //var count = 0
        //countText.text = count.toString()
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        countText.text = viewModel.count.toString()
        countBtn.setOnClickListener {
           // count++
            //countText.text = count.toString()
            viewModel.updateCount()
            countText.text = viewModel.count.toString()
        }
    }
}