package com.example.appchat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.calling.repository.MainRepository
import com.example.appchat.calling.utils.DataModel
import com.example.appchat.calling.utils.DataModelType
import com.example.appchat.videocall.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), UserAdapter.Listener, MainRepository.Listener {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var remoteView:org.webrtc.SurfaceViewRenderer
    private lateinit var localView:org.webrtc.SurfaceViewRenderer

    private lateinit var incomingNameTV: TextView
    private lateinit var incomingCallLayout:androidx.constraintlayout.widget.ConstraintLayout
    private lateinit var acceptButton:androidx.appcompat.widget.AppCompatButton
    private lateinit var rejectButton:androidx.appcompat.widget.AppCompatButton
    private lateinit var switchCameraButton:ImageView
    private lateinit var micButton:ImageView
    private lateinit var videoButton:ImageView
    private lateinit var endCallButton:ImageView
    private lateinit var callLayout:RelativeLayout
    private var isCameraMuted = false
    private var isMicrophoneMuted = false

    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private var username: String? = null
    private lateinit var mainRepository:MainRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRepository = MainRepository.getInstance()
        Log.d("MainActivity", "MainRepository initialized")

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        adapter = UserAdapter(this, userList, this)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        remoteView = findViewById(R.id.remote_view)
        localView= findViewById(R.id.local_view)
        incomingNameTV = findViewById(R.id.incomingCallTitleTv)
        incomingCallLayout = findViewById(R.id.incomingCallLayout)
        acceptButton = findViewById(R.id.acceptButton)
        rejectButton = findViewById(R.id.declineButton)
        switchCameraButton = findViewById(R.id.switch_camera_button)
        micButton = findViewById(R.id.mic_button)
        videoButton = findViewById(R.id.video_button)
        endCallButton = findViewById(R.id.end_call_button)
        callLayout = findViewById(R.id.callLayout)


        username = intent.getStringExtra("username")
        Log.i("GetUsername", "Username in main activity is = $username")


        mDbRef.child("chat_users").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout) {
            //sign out
            mAuth.signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        else if(item.itemId == R.id.menu_item_2) {
            val intent = Intent(this@MainActivity, MainActivity::class.java).apply {
                Log.i("MYDETAILS", "This is the username i am sending to call activity ${username}")
                putExtra("username", username)
            }
            startActivity(intent)
            return true
        }
        return true
    }

    override fun onVideoCallClicked(uid: String) {
//        val intent = Intent(this@MainActivity, CallActivity::class.java).apply {
//            Log.i("Debug", "video clicked This is the username i am sending to call activity ${username}")
//            putExtra("uid", username)
//        }
//        startActivity(intent)
        Log.d("MainActivity", "Calling function called")
        Log.d("MainActivity", "MainRepository: $mainRepository")
        //userRecyclerView.visibility = View.GONE
        calling(uid)
    }

    fun calling(uid:String) {
        //val uid = intent.getStringExtra("uid")
        // Hide the userRecyclerView
        userRecyclerView.visibility = View.GONE
       // userRecyclerView.visibility = View.GONE
        mainRepository.sendCallRequest(
            uid
        ) {
            Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show()
        }

        displayViews()

    }


    fun displayViews() {
        mainRepository.initLocalView(localView)
        mainRepository.initRemoteView(remoteView)
        mainRepository.listener = this

        //incomingCallLayout.visibility = View.VISIBLE
        //views.incomingCallLayout.setVisibility(View.GONE);
        //mainRepository.startCall(data.getSender());
        mainRepository.subscribeForLatestEvent { data: DataModel ->
            when (data.type) {
                DataModelType.StartCall -> {
                    Log.d("Data type", "data type is ${data.type}")
                    runOnUiThread {
                        // Disable the RecyclerView
                        userRecyclerView.visibility = View.GONE
                        incomingNameTV.text = data.sender + " is Calling you"
                        incomingCallLayout.visibility = View.VISIBLE
                        acceptButton.setOnClickListener { v ->
                            // Start the call here
                            mainRepository.startCall(data.sender)
                            incomingCallLayout.visibility = View.GONE
                            // Show the call layout
                            // callLayout.visibility = View.VISIBLE
                        }
                        rejectButton.setOnClickListener { v ->
                            incomingCallLayout.visibility = View.GONE
                            // Re-enable the RecyclerView
                            userRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }

                else -> {}

            }
        }

        switchCameraButton.setOnClickListener { mainRepository.switchCamera() }

        micButton.setOnClickListener { v ->
            if (isMicrophoneMuted) {
                micButton.setImageResource(R.drawable.ic_baseline_mic_off_24)
            } else {
                micButton.setImageResource(R.drawable.ic_baseline_mic_24)
            }
            mainRepository.toggleAudio(isMicrophoneMuted)
            isMicrophoneMuted = !isMicrophoneMuted
        }

        videoButton.setOnClickListener { v ->
            if (isCameraMuted) {
                videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24)
            } else {
                videoButton.setImageResource(R.drawable.ic_baseline_videocam_24)
            }
            mainRepository.toggleVideo(isCameraMuted)
            isCameraMuted = !isCameraMuted
        }

        endCallButton.setOnClickListener { v ->
            mainRepository.endCall()
            finish()
            // show the userRecyclerView
            userRecyclerView.visibility = View.VISIBLE

        }

    }
    override fun onAudioCallClicked(username: String) {
        TODO("Not yet implemented")
    }

    override fun webrtcConnected() {
        runOnUiThread {
            incomingCallLayout.setVisibility(View.GONE)
            //whoToCallLayout.setVisibility(View.GONE)
            callLayout.setVisibility(View.VISIBLE)
        }
    }

    override fun webrtcClosed() {
        runOnUiThread { finish() }
    }
}