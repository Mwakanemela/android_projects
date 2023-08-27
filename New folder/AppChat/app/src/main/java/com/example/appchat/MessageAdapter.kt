import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appchat.Message
import com.example.appchat.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    // MediaPlayer for audio
    private var mediaPlayer: MediaPlayer? = null

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
            //only text display
            holder.sentMessage.text = currentMessage.message
            holder.videoView.visibility = View.GONE
            holder.attachmentImage.visibility = View.GONE
            holder.attachmentIcon.visibility = View.GONE

            // Handle file attachment here
            if (!currentMessage.files.isNullOrBlank()) {
                holder.attachmentIcon.visibility = View.VISIBLE
                holder.attachmentIcon.setOnClickListener {
                    Log.i("MY-TAG", "Icon Clicked")
                    val fileUrl = currentMessage.files

                    // Determine file type and handle accordingly
                    when (currentMessage.fileType) {
                        "image" -> {
                            holder.attachmentIcon.visibility = View.GONE
                            holder.videoView.visibility = View.GONE
                            holder.attachmentImage.visibility = View.VISIBLE

                            Glide.with(context).load(fileUrl).into(holder.attachmentImage)
                        }
                        "audio" -> {
                            // Play audio
                            playAudio(fileUrl!!)
                        }
                        "video" -> {
                            // Play video
                            playVideo(holder.videoView, fileUrl!!)
                        }
                    }
                }
            } else {
                holder.attachmentIcon.visibility = View.GONE
            }
        } else if (holder is ReceiveViewHolder) {
            holder.receiveMessage.text = currentMessage.message
            holder.videoView.visibility = View.GONE
            holder.attachmentImage.visibility = View.GONE
            holder.attachmentIcon.visibility = View.GONE
            holder.attachmentIcon.setOnClickListener {
                Log.i("MYTAG", "Icon Clicked")
            }

            // Handle file attachment here
            if (!currentMessage.files.isNullOrBlank()) {
                holder.attachmentIcon.visibility = View.VISIBLE
                holder.attachmentIcon.setOnClickListener {
                    //Log.i("MYTAG", "Icon Clicked")
                    val fileUrl = currentMessage.files

                    // Determine file type and handle accordingly
                    when (currentMessage.fileType) {
                        "image" -> {
                            holder.attachmentIcon.visibility = View.GONE
                            holder.videoView.visibility = View.GONE
                            holder.attachmentImage.visibility = View.VISIBLE
                            Glide.with(context).load(fileUrl).into(holder.attachmentImage)
                        }
                        "audio" -> {
                            // Play audio
                            playAudio(fileUrl!!)
                        }
                        "video" -> {
                            // Play video
                            playVideo(holder.videoView, fileUrl!!)
                        }
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

    private fun playAudio(audioUrl: String) {
        // Stop any previous playback
        stopAudioPlayback()

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(audioUrl)
        mediaPlayer?.prepare()
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            stopAudioPlayback()
        }
    }

    private fun playVideo(videoView: VideoView, videoUrl: String) {
        videoView.visibility = View.VISIBLE
        val videoUri = Uri.parse(videoUrl)
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setOnCompletionListener {
                videoView.visibility = View.GONE
            }
        }
        videoView.start()
    }

    private fun stopAudioPlayback() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.txt_sent_message)
        val attachmentIcon: ImageView = itemView.findViewById(R.id.attachment_icon)
        val videoView: VideoView = itemView.findViewById(R.id.video_view)
        val attachmentImage: ImageView = itemView.findViewById(R.id.attachment_image)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.txt_receive_message)
        val attachmentIcon: ImageView = itemView.findViewById(R.id.attachment_icon)
        val videoView: VideoView = itemView.findViewById(R.id.video_view)

        val attachmentImage: ImageView = itemView.findViewById(R.id.attachment_image)

    }
}
