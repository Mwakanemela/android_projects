package com.example.oopdemo

import android.util.Log

class Driver(name: String) {
    //var driverName: String = ""
    private lateinit var driverName: String
    init {
        driverName = name
    }

    fun showDriverName():Unit
    {
        Log.i("My Tag", "Driver name is $driverName")
    }
}