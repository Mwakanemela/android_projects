package com.example.uploadretrieveimages

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uploadretrieveimages.databinding.ActivityImagesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ImagesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityImagesBinding

    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    private var mList = mutableListOf<String>()
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVars()
        getImages()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getImages() {
        firebaseFirestore.collection("images")
            .get().addOnSuccessListener {
                for(i in it) {
                    mList.add(i.data["pic"].toString())
                }
            adapter.notifyDataSetChanged()
            }
    }

    private fun initVars() {
        storageRef = FirebaseStorage.getInstance().reference.child("images")
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ImagesAdapter(mList)
        binding.recyclerView.adapter = adapter
    }
}