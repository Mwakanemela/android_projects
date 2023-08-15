package com.example.firstapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firstapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.i("My tag", "SecondActivity : onCreate")

        val username = intent.getStringExtra("Username")
        val textView = findViewById<TextView>(R.id.offersTextView)
        val message = "$username, you are now an android programmer"
        textView.text = message
    }


    override fun onStart() {
        super.onStart()
        Log.i("My tag", "SecondActivity : onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i("My tag", "SecondActivity : onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.i("My tag", "SecondActivity : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("My tag", "SecondActivity : onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("My tag", "SecondActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("My tag", "SecondActivity : onRestart")
    }
}