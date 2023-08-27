package com.example.appchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.appchat.calling.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
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

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.loginBtn)
        btnRegister =findViewById(R.id.registerBtn)

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
                                    Log.i("GetUsername", "Current userid is ${mAuth.currentUser?.uid}")
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