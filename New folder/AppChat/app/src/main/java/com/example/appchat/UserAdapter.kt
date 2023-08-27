package com.example.appchat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class UserAdapter(val context: Context, val userList: ArrayList<User>,   private val listener:Listener): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    interface  Listener {
        fun onVideoCallClicked(username:String)
        fun onAudioCallClicked(username:String)
    }
    //private var mainRepository: MainRepository? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_main_recycler_view, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            Log.i("Debug", "Current User username: ${currentUser.name}")
            intent.putExtra("uid", currentUser?.uid)
            context.startActivity(intent)
        }
        holder.videoCall.setOnClickListener {
            listener.onVideoCallClicked(currentUser.uid!!)
        }


    }




    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.usernameTv)
        val videoCall: ImageView = itemView.findViewById(R.id.videoCallBtn)
    }

}