package com.example.appchat

import MessageAdapter
import android.app.Activity
import android.content.Intent
import android.view.View
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class ChatActivity : AppCompatActivity() {


    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var uploadButton: ImageView

    private lateinit var audioRecord: ImageView
    private lateinit var stopRecord:ImageView

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var mDbRef: DatabaseReference
    private lateinit var storageRef: StorageReference // Add this

    private var sFileUri: Uri? = null // Updated variable name
    private var receiverRoom: String? = null
    private var senderRoom: String? = null
    val senderUid = FirebaseAuth.getInstance().currentUser?.uid

    private lateinit var outputFile: String
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        mDbRef = FirebaseDatabase.getInstance().reference
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title= name
        // Initialize Firebase Storage reference
        val storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        // Initialize views and adapters
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        uploadButton = findViewById(R.id.uploadButton)
        audioRecord = findViewById(R.id.audioRecordButton)
        stopRecord = findViewById(R.id.audioStopButton)

        //VN Record code start
        outputFile = "${externalCacheDir?.absolutePath}/audiorecord.3gp"
        audioRecord.setOnClickListener {
            startRecording()
            audioRecord.visibility = View.GONE
            stopRecord.visibility = View.VISIBLE
        }

        stopRecord.setOnClickListener {
            stopRecording()
            stopRecord.visibility = View.GONE
            audioRecord.visibility = View.VISIBLE
        }





        //VN Record Ends






        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        // Attach value event listener for messages
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled
                }
            })

        // Send button click listener
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid!!, "", "") // No attachment for regular messages

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject)
                .addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

        // Upload button click listener
        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, REQUEST_CODE_FILE_PICKER)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == Activity.RESULT_OK) {
            sFileUri = data?.data
            Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show()

            // Get the file format (extension)
            val fileName = sFileUri?.lastPathSegment
            Log.i("DEBUG_TAG", "sFileUri $sFileUri")
            Log.i("DEBUG_TAG", "Filename $fileName")
            //val fileFormat = fileName?.substringAfterLast(".")
            val lastDotIndex = fileName?.lastIndexOf(".")
            Log.i("DEBUG_TAG", "lastDotIndex $lastDotIndex")
            //val fileFormat = fileName?.substring(lastDotIndex?.plus(1) ?: 0)
            //val fileFormat = "PNG"

            if (lastDotIndex != null && lastDotIndex >= 0) {
                val fileFormat = fileName.substring(lastDotIndex + 1)
                Log.i("DEBUG_TAG", "File Format: $fileFormat")

                Log.i("DEBUG_TAG", "File Format: $fileFormat")
                // Upload the file to Firebase Storage
                val fileRef = storageRef.child("files/$fileName")
                Log.i("DEBUG_TAG", "File Format: $fileFormat")
                val uploadTask = fileRef.putFile(sFileUri!!)

                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    fileRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result.toString()

                        // Determine the file type based on the format
                        val fileType = when (fileFormat) {
                            "jpeg", "jpg", "png", "PNG" -> "image"
                            "mp3" -> "audio"
                            "mp4" -> "video"
                            else -> "other" // You can add more cases if needed
                        }

                        // Create a message object with attachment URL and send it
                        val messageObject = Message("", senderUid!!, downloadUrl, fileType)

                        mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                            .setValue(messageObject)
                            .addOnSuccessListener {
                                mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                                    .setValue(messageObject)
                            }
                    } else {
                        // Handle upload failure
                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                // Rest of your code for uploading and handling the file...
            }


            }
        }
    }


    companion object {
        private const val REQUEST_CODE_FILE_PICKER = 123
    }


    //start recording VN
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

    //stop recording VN
    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        // Generate a unique audio file name (you can use a timestamp, for example)
        val audioFileName = "audio_${System.currentTimeMillis()}.3gp"

        // Upload the audio to Firebase Storage with the generated file name
        uploadAudioToFirebase(audioFileName)
    }

    private fun uploadAudioToFirebase(audioFileName: String) {
        var audioFileUri = Uri.fromFile(File(outputFile))
        val audioFileRef = storageRef.child("files/$audioFileName")

        val uploadTask = audioFileRef.putFile(audioFileUri)


        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Get the download URL of the uploaded audio
            audioFileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val downloadUrl = downloadUri.toString()

                // Determine the file type based on the format (assuming it's an audio file)
                val fileType = "audio"

                // Create a message object with attachment URL and send it
                val messageObject = Message("", senderUid!!, downloadUrl, fileType)
                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject)
                    .addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageObject)
                            .addOnSuccessListener {
                                // Optionally, clear the URI and file name after successful upload
                                audioFileUri = null

                            }
                    }
                // File successfully uploaded to Firebase Storage
                Log.i("MyTag", "VN Uploaded")

                // You can perform further actions or show a success message here
            }.addOnFailureListener {
                // Handle the failure of the upload
            }
        }
    }
}