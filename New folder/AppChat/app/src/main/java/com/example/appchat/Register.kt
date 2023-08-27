package com.example.appchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging


class Register : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var TokenEt:EditText


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //disable action bar
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtName = findViewById(R.id.edt_name)


        TokenEt = findViewById(R.id.tokenEditText)


        FirebaseMessaging.getInstance().token.addOnSuccessListener { result ->
            if(result != null){
                //fbToken = result
                // DO your thing with your firebase token
                TokenEt.setText(result)

            }
        }



        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)


        btnRegister =findViewById(R.id.registerBtn)
        btnRegister.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    addUserToDatabaseForVideoCall(mAuth.currentUser?.uid!!)
                    val intent = Intent(this@Register, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Register, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("chat_users").child(uid).setValue(User(name, email, uid, TokenEt.text.toString()))
    }

    private fun addUserToDatabaseForVideoCall(uid: String) {
        // mDbRef = FirebaseDatabase.getInstance().getReference()
        val dbRef = FirebaseDatabase.getInstance().getReference("video_users")
        dbRef.child(uid).setValue("")
    }
}