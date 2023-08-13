package com.example.clapapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val play  = findViewById<FloatingActionButton>(R.id.fabPlay)
        val pause = findViewById<FloatingActionButton>(R.id.fabPause)
        val stop = findViewById<FloatingActionButton>(R.id.fabStop)

        val btnVideo = findViewById<Button>(R.id.videoBtn)
        btnVideo.setOnClickListener {
            val intent = Intent(this, VideoViewDemo::class.java)
            startActivity(intent)
        }

        seekBar = findViewById(R.id.audioSB)
        handler = Handler(Looper.getMainLooper())

        play.setOnClickListener {
            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.life_we_living)
                initializeSeekBar()
            }
            mediaPlayer?.start()
        }
        pause.setOnClickListener {
            mediaPlayer?.pause()
        }
        stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer= null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }
        seekBar.setOnClickListener {

        }

    }

    private fun initializeSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }


        })
        val tvPlayed = findViewById<TextView>(R.id.tvStart)
        val tvDuration = findViewById<TextView>(R.id.tvEnd)

        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition

            val playedTime = mediaPlayer!!.currentPosition/1000
            tvPlayed.text = "$playedTime sec"
            val duration = mediaPlayer!!.duration/1000
            val dueTime = duration - playedTime
            tvDuration.text = "$dueTime sec"
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
}