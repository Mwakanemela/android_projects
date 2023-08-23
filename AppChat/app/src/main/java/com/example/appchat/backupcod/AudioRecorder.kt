package com.example.appchat.backupcod

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.appchat.R
import java.io.IOException

class AudioRecorder : AppCompatActivity() {

    private lateinit var recordButton: Button
    private lateinit var stopRecordButton: Button
    private lateinit var playButton: Button

    private lateinit var outputFile: String
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_recorder)

        recordButton = findViewById(R.id.recordButton)
        stopRecordButton = findViewById(R.id.stopRecordButton)
        playButton = findViewById(R.id.playButton)

        outputFile = "${externalCacheDir?.absolutePath}/audiorecord.3gp"

        recordButton.setOnClickListener {
            startRecording()
            recordButton.visibility = View.GONE
            stopRecordButton.visibility = View.VISIBLE
        }

        stopRecordButton.setOnClickListener {
            stopRecording()
            stopRecordButton.visibility = View.GONE
            playButton.visibility = View.VISIBLE
        }

        playButton.setOnClickListener {
            playRecordedAudio()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(outputFile)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }

    private fun playRecordedAudio() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(outputFile)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaPlayer?.release()
    }
}
