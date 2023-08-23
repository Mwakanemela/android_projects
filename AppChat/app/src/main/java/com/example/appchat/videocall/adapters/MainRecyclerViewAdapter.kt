package com.example.appchat.videocall.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.ChatActivity
import com.example.appchat.R
import com.example.appchat.User
import com.example.appchat.databinding.ItemMainRecyclerViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRecyclerViewAdapter(private val listener:Listener) : RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder>() {

    private var usersList:List<Pair<String,String>>?=null
    fun updateList(list:List<Pair<String,String>>){
        this.usersList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val binding = ItemMainRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return MainRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList?.size?:0
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        usersList?.let { list->
            val user = list[position]
            holder.bind(user,{
                listener.onVideoCallClicked(it)
            },{
                listener.onAudioCallClicked(it)
            })
        }
    }

    interface  Listener {
        fun onVideoCallClicked(username:String)
        fun onAudioCallClicked(username:String)
    }



    class MainRecyclerViewHolder(private val binding: ItemMainRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root){
        private val context = binding.root.context

        // Retrieve user's name from Realtime Database
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.reference.child("chat_users") // Adjust the reference path as needed
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser?.uid
        lateinit var username:String

        private fun fetchUserData(userId: String, callback: (String?) -> Unit) {
            usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    val username = user?.name
                    callback(username)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if needed
                    Log.e("MYDETAILS", "Error fetching user data: ${error.message}")
                    callback(null)
                }
            })
        }

        fun bind(
            user:Pair<String,String>,
            videoCallClicked:(String) -> Unit,
            audioCallClicked:(String)-> Unit
        ){
            binding.apply {
                fetchUserData(user.first) { username ->
                    if (username != null) {
                        usernameTv.text = username

                        //usernameTv.text = user.first
                        usernameTv.setOnClickListener {
                            // Start the desired activity when usernameTv is clicked
                            // Authentication was successful, get the Firebase user ID

                            try {
                                val context = itemView.context
                                val intent = Intent(context, ChatActivity::class.java)
                                // Pass any data you need to the new activity
                                intent.putExtra("name", username)
                                intent.putExtra("uid", userId)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Log.e("MYDETAILS", "Error starting ChatActivity: ${e.message}")
                            }
                        }
                    } else {
                        // Handle the case where username couldn't be fetched
                        Log.e("MYDETAILS", "Error fetching username")
                    }
                }

                when (user.second) {
                    "ONLINE" -> {
                        videoCallBtn.isVisible = true
                        audioCallBtn.isVisible = true
                        videoCallBtn.setOnClickListener {
                            Log.i("MYDETAILS", "Video call button clicked")

                            Log.i("MYDETAILS", "User first value ${user.first}")
                            Log.i("MYDETAILS", "User second value ${user.second}")
                            videoCallClicked.invoke(user.first)

                        }
                        audioCallBtn.setOnClickListener {
                            audioCallClicked.invoke(user.first)
                        }
                        statusTv.setTextColor(context.resources.getColor(R.color.light_green, null))
                        statusTv.text = "Online"
                    }
                    "OFFLINE" -> {
                        videoCallBtn.isVisible = false
                        audioCallBtn.isVisible = false
                        statusTv.setTextColor(context.resources.getColor(R.color.red, null))
                        statusTv.text = "Offline"
                    }
                    "IN_CALL" -> {
                        videoCallBtn.isVisible = false
                        audioCallBtn.isVisible = false
                        statusTv.setTextColor(context.resources.getColor(R.color.yellow, null))
                        statusTv.text = "In Call"
                    }
                }

            }



        }



    }
}