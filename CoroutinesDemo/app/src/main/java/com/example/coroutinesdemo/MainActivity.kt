package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private  var count = 0
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.tvCount)
        val countButton = findViewById<Button>(R.id.countBtn)
        val downloadButton = findViewById<Button>(R.id.downloadBtn)

        messageTextView = findViewById(R.id.tvMessage)

        countButton.setOnClickListener {
            textView.text = count++.toString()
        }

        downloadButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                downloadButtonData()
            }

        }
    }

    private suspend fun downloadButtonData(){
        for(i in 1..200000) {
            Log.i("My Tag", "Downloading user $i in ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                messageTextView.text = "Downloading User $i"
            }

            delay(200)
        }
    }
}