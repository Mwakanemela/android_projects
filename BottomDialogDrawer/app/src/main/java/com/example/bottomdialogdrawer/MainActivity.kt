package com.example.bottomdialogdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.bottomdialogdrawer.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            sampleOneBtn.setOnClickListener {

                showDialogOne()
            }
            sampleTwoBtn.setOnClickListener {
                showDialogTwo()
            }
        }
    }

    private fun showDialogOne() {

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.sample_one)
        val btnImages = dialog.findViewById<TextView>(R.id.imageTv)
        val btnAudio = dialog.findViewById<TextView>(R.id.audioTv)
        val btnVideo = dialog.findViewById<TextView>(R.id.videos)

        btnImages?.setOnClickListener {
            Toast.makeText(this, "Images Clicked", Toast.LENGTH_LONG).show()
        }
        btnAudio?.setOnClickListener {
            Toast.makeText(this, "Audio Clicked", Toast.LENGTH_LONG).show()
        }
        btnVideo?.setOnClickListener {
            Toast.makeText(this, "Video Clicked", Toast.LENGTH_LONG).show()
        }

        dialog.show()


    }

    private fun showDialogTwo() {

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.sample_two)
        val btnImages = dialog.findViewById<TextView>(R.id.imageTv)
        val btnAudio = dialog.findViewById<TextView>(R.id.audioTv)
        val btnVideo = dialog.findViewById<TextView>(R.id.videos)

        btnImages?.setOnClickListener {
            //Toast.makeText(this, "Images Clicked", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        btnAudio?.setOnClickListener {
            Toast.makeText(this, "Audio Clicked", Toast.LENGTH_LONG).show()
        }
        btnVideo?.setOnClickListener {
            Toast.makeText(this, "Video Clicked", Toast.LENGTH_LONG).show()
        }
        dialog.setCancelable(false)
        dialog.show()


    }
}