package com.example.kotlinsendnotification

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kotlinsendnotification.databinding.ActivityFcmnotificationBinding
import com.example.kotlinsendnotification.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val TOPIC = "/topics/myTopic"

class FCMNotification : AppCompatActivity() {

    val TAG = "FCMNotification"
    private lateinit var binding:ActivityFcmnotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        // Initialize the binding object
        //&& recipientToken.isNotEmpty()

        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
               // fbToken = result
                // DO your thing with your firebase token
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        binding = ActivityFcmnotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSend.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()
            //val recipientToken = binding.etToken.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message),
                    TOPIC
                    //recipientToken

                ).also {
                    sendNotification(it)
                }
            }
        }
    }


    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}