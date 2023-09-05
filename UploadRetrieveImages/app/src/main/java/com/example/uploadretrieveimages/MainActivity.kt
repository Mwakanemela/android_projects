package com.example.uploadretrieveimages

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.uploadretrieveimages.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private lateinit var storageRef:StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    private var imageUri:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initVars()
        registerClickEvents()
    }

    private fun registerClickEvents() {
        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }
        binding.showAllBtn.setOnClickListener {
            val intent = Intent(this, ImagesActivity::class.java)
            startActivity(intent)
        }
        binding.imageView.setOnClickListener {
            //get images from storage
            resultLauncher.launch("image/*")
        }
    }

    private fun uploadImage() {
        binding.progressBar.visibility = View.VISIBLE
        storageRef = storageRef.child(System.currentTimeMillis().toString())
        //storageRef.putFile(imageUri!!)
        imageUri?.let { storageRef.putFile(it).addOnCompleteListener{
            task ->
            if(task.isSuccessful){
                //add image to fire storage
                storageRef.downloadUrl.addOnSuccessListener {
                    uri->
                    val map = HashMap<String, Any>()
                    map["pic"] = uri.toString()
                    firebaseFirestore.collection("images").add(map).addOnCompleteListener {
                        firestoreTask->
                        if(firestoreTask.isSuccessful)
                        {
                            Toast.makeText(this, "Image Upload Success", Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_LONG).show()
                        }

                        binding.progressBar.visibility = View.GONE
                        binding.imageView.setImageResource(R.drawable.vector)
                    }
                }
            }else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                binding.progressBar.visibility = View.GONE
                binding.imageView.setImageResource(R.drawable.vector)
            }
        } }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){
        imageUri = it
        binding.imageView.setImageURI(it)
    }
    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("images")
        firebaseFirestore = FirebaseFirestore.getInstance()
    }
}