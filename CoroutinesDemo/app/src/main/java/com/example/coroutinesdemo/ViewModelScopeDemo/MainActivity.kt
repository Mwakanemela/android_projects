package com.example.coroutinesdemo.ViewModelScopeDemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinesdemo.R


class MainActivity : AppCompatActivity() {

private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getUserData()
        mainActivityViewModel.users.observe(this, Observer {
            myUsers->
            myUsers?.forEach {
                Log.i("MyTag", "name is ${it.name}")
            }
        })
    }

}