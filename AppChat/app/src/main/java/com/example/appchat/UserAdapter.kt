package com.example.appchat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.calling.repository.MainRepository
import com.example.appchat.calling.ui.CallActivity
import com.example.appchat.calling.utils.DataModelType
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
//        holder.videoCall.setOnClickListener {
//            Log.i("Debug", "Video call button clicked")
//            Log.i("Debug", "Current User Uid ${currentUser.uid}")
//            //holder.userRecyclerView.isVisible = false
//            //holder.callLayout.isVisible = true
//            // Request camera and audio recording permissions
//            PermissionX.init(context as AppCompatActivity)
//                .permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//                .request { allGranted, grantedList, deniedList ->
//                    if (allGranted) {
//                        // Permissions granted, initiate the video call
//                        val mainRepository = MainRepository.getInstance()
//                        mainRepository.login(currentUser.uid, context) {
//                            // If successful, start the CallActivity
//                            //val callIntent = Intent(context, CallActivity::class.java)
//                           // context.startActivity(callIntent)
//                            //        views.callBtn.setOnClickListener(v->{
//
//                            mainRepository.sendCallRequest(
//                                currentUser.uid
//                            ) {
//                                Toast.makeText(context, "couldn't find the target", Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//
//                            //startActivity(Intent(context, CallActivity::class.java))
//
////                            mainRepository.initLocalView(holder.localView)
////                            mainRepository.initRemoteView(holder.remoteView)
////                            mainRepository.listener = this
//
////                            mainRepository.subscribeForLatestEvent { data ->
////                                if (data.type == DataModelType.StartCall) {
////                                    lifecycleScope.launch {
////                                        // Switch to the UI thread
////                                        withContext(Dispatchers.Main) {
////                                            holder.incomingNameTV!!.text = "${data.sender} is Calling you"
////                                            holder.incomingCallLayout!!.visibility = View.VISIBLE
////                                            holder.acceptButton!!.setOnClickListener { v ->
////                                                // Start the call here
////                                                mainRepository.startCall(data.sender)
////                                                val callIntent = Intent(context, CallActivity::class.java)
////                                                context.startActivity(callIntent)
////                                                holder.incomingCallLayout.visibility = View.GONE
////                                            }
////                                            holder.rejectButton!!.setOnClickListener { v ->
////                                                holder.incomingCallLayout.visibility = View.GONE
////                                            }
////                                        }
////                                    }
////                                }
////                            }
//
//                        }
//                    } else {
//                        // Handle denied permissions
//                        // You can show a message to the user or take other actions here
//                    }
//                }
//        }

    }




    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.usernameTv)
        val videoCall: ImageView = itemView.findViewById(R.id.videoCallBtn)
    }

}