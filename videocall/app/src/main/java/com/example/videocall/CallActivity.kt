package com.example.videocall

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.videocall.databinding.ActivityCallBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.UUID

class CallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallBinding
    var username = ""
    var friendsUsername = ""

    var isPeerConnected = false


    //mDbRef.child("chat_users").child(uid).setValue(User(name, email, uid))
    //private lateinit var mDbRef: DatabaseReference
    //var firebaseRef = Firebase.database.getReference("users")
    //mDbRef = FirebaseDatabase.getInstance().getReference()
    // Get a reference to the Firebase Realtime Database
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val firebaseRef: DatabaseReference = firebaseDatabase.getReference("vusers")

    var isAudio = true
    var isVideo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("username")!!

        Log.i("Username", "Username Call Activity: ${username}")

        //firebaseRef = FirebaseDatabase.getInstance().getReference()
        Log.i("Username", "database reference: ${firebaseRef}")
        binding.callBtn.setOnClickListener {
            friendsUsername = binding.friendNameEdit.text.toString()
            sendCallRequest()
        }

        binding.toggleAudioBtn.setOnClickListener {
            isAudio = !isAudio
            callJavascriptFunction("javascript:toggleAudio(\"${isAudio}\")")
            binding.toggleAudioBtn.setImageResource(if (isAudio) R.drawable.baseline_mic_24 else R.drawable.baseline_mic_off_24 )
        }

        binding.toggleVideoBtn.setOnClickListener {
            isVideo = !isVideo
            callJavascriptFunction("javascript:toggleVideo(\"${isVideo}\")")
            binding.toggleVideoBtn.setImageResource(if (isVideo) R.drawable.baseline_videocam_24 else R.drawable.baseline_videocam_off_24 )
        }

        setupWebView()
    }

    private fun sendCallRequest() {
        if (!isPeerConnected) {
            Toast.makeText(this, "You're not connected. Check your internet", Toast.LENGTH_LONG).show()
            return
        }

        firebaseRef.child(friendsUsername).child("incoming").setValue(username)
        firebaseRef.child(friendsUsername).child("isAvailable").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.value.toString() == "true") {
                    listenForConnId()
                }

            }

        })

    }

    private fun listenForConnId() {
        firebaseRef.child(friendsUsername).child("connId").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == null)
                    return
                switchToControls()
                callJavascriptFunction("javascript:startCall(\"${snapshot.value}\")")
            }

        })
    }

    private fun setupWebView() {

        binding.webView.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }
        }

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.mediaPlaybackRequiresUserGesture = false
        binding.webView.addJavascriptInterface(JavascriptInterface(this), "Android")

        loadVideoCall()
    }

    private fun loadVideoCall() {
        val filePath = "file:android_asset/call.html"
        binding.webView.loadUrl(filePath)

        binding.webView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                initializePeer()
            }
        }
    }

    var uniqueId = ""

    private fun initializePeer() {

        uniqueId = getUniqueID()

        callJavascriptFunction("javascript:init(\"${uniqueId}\")")
        firebaseRef.child(username).child("incoming").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                onCallRequest(snapshot.value as? String)
            }

        })

    }

    private fun onCallRequest(caller: String?) {
        if (caller == null) return

        binding.callLayout.visibility = View.VISIBLE
        binding.incomingCallTxt.text = "$caller is calling..."

        binding.acceptCall.setOnClickListener {
            firebaseRef.child(username).child("connId").setValue(uniqueId)
            firebaseRef.child(username).child("isAvailable").setValue(true)

            binding.callLayout.visibility = View.GONE
            switchToControls()
        }

        binding.rejectCall.setOnClickListener {
            firebaseRef.child(username).child("incoming").setValue(null)
            binding.callLayout.visibility = View.GONE
        }

    }

    private fun switchToControls() {
        binding.inputLayout.visibility = View.GONE
        binding.callControlLayout.visibility = View.VISIBLE
    }


    private fun getUniqueID(): String {
        return UUID.randomUUID().toString()
    }

    private fun callJavascriptFunction(functionString: String) {
        binding.webView.post { binding.webView.evaluateJavascript(functionString, null) }
    }


    fun onPeerConnected() {
        isPeerConnected = true
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        firebaseRef.child(username).setValue(null)
        binding.webView.loadUrl("about:blank")
        super.onDestroy()
    }

}

/*
*
* Certainly, here are the steps to deploy a Node.js signaling server on Vercel:

**1. Set Up Your Vercel Account:**

If you don't have a Vercel account, sign up for one at [vercel.com](https://vercel.com/). You can sign in using your GitHub, GitLab, or Bitbucket account.

**2. Install the Vercel CLI:**

The Vercel Command Line Interface (CLI) allows you to deploy and manage your projects. You can install it globally using npm:

```bash
npm install -g vercel
```

**3. Prepare Your Node.js Signaling Server:**

Ensure that your Node.js signaling server is ready for deployment. This includes having all the required dependencies and a `package.json` file with appropriate scripts for starting the server.

**4. Initialize a Vercel Project:**

Navigate to the root directory of your signaling server project and run the following command to initialize a Vercel project:

```bash
vercel init
```

This command will guide you through a series of prompts to configure your project. You'll need to choose a name for your project, link it to your Vercel account, and configure the deployment settings. When prompted for the "Framework preset," select "Other."

**5. Configure Build and Start Commands:**

In your Vercel project directory, you'll find a `vercel.json` file. Open this file and add the following configuration for your Node.js server:

```json
{
  "builds": [
    {
      "src": "server.js", // Replace with the entry point of your server
      "use": "@vercel/node"
    }
  ]
}
```

Replace `"server.js"` with the appropriate entry point of your Node.js server.

**6. Deploy Your Server:**

Deploy your server to Vercel by running the following command in your project directory:

```bash
vercel
```

Follow the prompts to deploy your project. Vercel will build and deploy your Node.js server.

**7. Access the Deployment URL:**

Once the deployment is complete, Vercel will provide you with a unique URL where your Node.js signaling server is hosted. You can use this URL to access your server and set it as your signaling server endpoint in your Android video call app.

**8. Configure Environment Variables (Optional):**

If your signaling server relies on environment variables, you can configure them in the Vercel project settings. Navigate to your project on the Vercel website, go to "Settings," and then to the "Environment Variables" section. Here, you can add and manage environment variables.

**9. Update Your Android App:**

In your Android video call app, update the configuration to use the URL of your deployed signaling server as the signaling server endpoint.

Your Node.js signaling server is now hosted on Vercel and can be accessed by users from different locations. Vercel also provides automatic scaling and global content delivery, which can help ensure reliable performance for your signaling server.
*
*  */