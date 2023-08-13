package com.example.clapapp

import android.net.Uri
import android.net.Uri.parse
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.clapapp.databinding.ActivityVideoViewDemoBinding

class VideoViewDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view_demo)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        val uri: Uri = parse("android.resource://"+packageName+"/"+R.raw.gage_road)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
    }

}