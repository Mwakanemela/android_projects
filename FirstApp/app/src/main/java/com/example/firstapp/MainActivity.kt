package com.example.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("My tag", "On create start")

        val greetingTextView = findViewById<TextView>(R.id.tvHello)
        val inputField = findViewById<EditText>(R.id.nameInputField)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val offersButton = findViewById<Button>(R.id.offersButton)
        var enteredName = ""
        submitButton.setOnClickListener {
            enteredName = inputField.text.toString()
            if(enteredName == "")
            {

                offersButton.visibility = INVISIBLE
                greetingTextView.text = ""
                Toast.makeText(this@MainActivity,"Please, enter your name", Toast.LENGTH_SHORT).show()
            }else {
                val message = "Welcome $enteredName"
                Log.i("My tag", message)
                greetingTextView.text = message

                inputField.text.clear()
                offersButton.visibility = VISIBLE
            }
        }
        offersButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("Username", enteredName)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("My tag", "MainActivity : onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i("My tag", "MainActivity : onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.i("My tag", "MainActivity : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("My tag", "MainActivity : onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("My tag", "MainActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("My tag", "MainActivity : onRestart")
    }
}