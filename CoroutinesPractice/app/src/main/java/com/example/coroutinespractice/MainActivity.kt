package com.example.coroutinespractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinespractice.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        GlobalScope.launch(Dispatchers.IO){

            Log.d(TAG, "in coroutine dispatchers io in ${Thread.currentThread().name}")

            withContext(Dispatchers.Main) {
                Log.d(TAG, "setting text in ${Thread.currentThread().name}")
                val networkCallAnswer = doNetworkCall()

                binding.tv.text= networkCallAnswer

            }



        }


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