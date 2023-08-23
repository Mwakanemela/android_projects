package com.example.appchat

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.appchat.calling.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.permissionx.guolindev.PermissionX

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var mAuth: FirebaseAuth
    private var username: String? = null
    var mainRepository: MainRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //disable action bar
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()



        // Retrieve user's name from Realtime Database
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.reference.child("chat_users") // Adjust the reference path as needed

        usersRef.child(mAuth.currentUser?.uid ?: "").child("name")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.value as? String
                    if (name != null) {
                        username = name
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                }
            })

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.loginBtn)
        btnRegister =findViewById(R.id.registerBtn)
        username = intent.getStringExtra("username")

        btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            finish()
            startActivity(intent)

        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    mainRepository = MainRepository.getInstance()
                    PermissionX.init(this)
                        .permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                        .request { allGranted: Boolean, grantedList: List<String?>?, deniedList: List<String?>? ->
                            if (allGranted) {
                                //login to firebase here
                                mainRepository!!.login(
                                    mAuth.currentUser?.uid, applicationContext
                                ) {
                                    //if success then we want to move to call activity
                                    val intent = Intent(this@Login, MainActivity::class.java).apply {
                                        putExtra("username",username)
                                        Log.i("GetUsername", "Your user name is $username")
                                    }

                                    startActivity(intent)
                                }
                            }
                        }



            } else {
                    Toast.makeText(this@Login, "User nah Exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}