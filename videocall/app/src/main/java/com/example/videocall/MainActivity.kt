package com.example.videocall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
    val requestCode = 1

   // val firebaseDatabase = FirebaseDatabase.getInstance()
    //val usersRef = firebaseDatabase.getReference("video_users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isPermissionGranted()) {
            askPermissions()
        }
        //usersRef.setValue(usernameEdit)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val usernameEdit = findViewById<EditText>(R.id.usernameEdit)
        Firebase.initialize(this)
        loginBtn.setOnClickListener {
            val username = usernameEdit.text.toString()
            //usersRef.setValue(username)
            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("username", username)
            Log.i("Username", "Username Main Activity: ${username}")
            startActivity(intent)
        }

    }

    private fun askPermissions() {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun isPermissionGranted():Boolean {
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    this, it
            ) != PackageManager.PERMISSION_GRANTED)
            {
                return false
            }
        }
        return true
    }
}