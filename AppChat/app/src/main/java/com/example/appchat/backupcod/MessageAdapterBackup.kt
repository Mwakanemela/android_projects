package com.example.appchat.backupcod

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appchat.Message
import com.example.appchat.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == ITEM_RECEIVE) {
            val view: View = inflater.inflate(R.layout.recieve, parent, false)
            ReceiveViewHolder(view)
        } else {
            val view: View = inflater.inflate(R.layout.sent, parent, false)
            SentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder is SentViewHolder) {
            holder.sentMessage.text = currentMessage.message
        } else if (holder is ReceiveViewHolder) {
            holder.receiveMessage.text = currentMessage.message

            // Handle file attachment here
            if (!currentMessage.files.isNullOrBlank()) {
                holder.attachmentIcon.visibility = View.VISIBLE
                holder.attachmentIcon.setOnClickListener {
                    // Handle attachment click
                    val fileUrl = currentMessage.files
                    // Implement file viewing or downloading logic here
                    if(fileUrl != null) {
                        viewOrDownloadFile(context, fileUrl)
                    }
                }
            } else {
                holder.attachmentIcon.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid == currentMessage.senderId) {
            ITEM_SENT
        } else {
            ITEM_RECEIVE
        }
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.txt_sent_message)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.txt_receive_message)
        val attachmentIcon: ImageView = itemView.findViewById(R.id.attachment_icon)
    }
}

fun viewOrDownloadFile(context: Context, fileUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(fileUrl)
    context.startActivity(intent)
}