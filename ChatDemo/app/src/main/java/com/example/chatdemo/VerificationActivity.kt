package com.example.chatdemo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.chatdemo.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth

class VerificationActivity : AppCompatActivity() {

    var binding: ActivityVerificationBinding? = null
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser != null) {
            val intent = Intent(this@VerificationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()
        binding!!.etNumber.requestFocus()
        binding!!.continueBtn.setOnClickListener {
            val intent = Intent(this@VerificationActivity, OTPActivity::class.java)
            intent.putExtra("phoneNumber", binding!!.etNumber.text.toString())
            startActivity(intent)
        }
    }
}