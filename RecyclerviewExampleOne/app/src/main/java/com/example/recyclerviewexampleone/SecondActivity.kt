package com.example.recyclerviewexampleone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewexampleone.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val image = intent.getIntExtra("image", 0 )

        binding.sImageView.setImageResource(image)
        binding.sDescriptionTv.text = desc
        binding.sTvName.text = name
    }
}