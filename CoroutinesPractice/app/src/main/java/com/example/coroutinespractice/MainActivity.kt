package com.example.coroutinespractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinespractice.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Before runBlocking")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "IO Coroutine")
            }
            Log.d(TAG, "Start run blocking")
            delay(5000L)
            Log.d(TAG, "finished runBlocking")
        }
        Log.d(TAG, "After runBlocking")
    }

    private suspend fun doNetworkCall():String {
        delay(3000L)
        return "Answer from network call 1"
    }

    suspend fun doNetworkCall2():String {
        delay(3000L)
        return "Answer from network call 2"
    }
}