package com.example.intentpractice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val explicitIntentButton = findViewById<Button>(R.id.explicitIntentButton)

        explicitIntentButton.setOnClickListener {
            val explicitIntent = Intent(this, SecondActivity::class.java)
            startActivity(explicitIntent)
            finish() //so that when user clicks the back button it exit the app
        }

        //implicit intent
        var url = "https://github.com/Mwakanemela"
        val implicitIntentButton = findViewById<Button>(R.id.implicitIntentButton)

        implicitIntentButton.setOnClickListener {
            val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(implicitIntent)
        }
    }
}