package com.example.appchat.videocall.firebaseClient

import com.example.appchat.videocall.utils.DataModel
import com.example.appchat.videocall.utils.FirebaseFieldNames.LATEST_EVENT
import com.example.appchat.videocall.utils.FirebaseFieldNames.STATUS
import com.example.appchat.videocall.utils.MyEventListener
import com.example.appchat.videocall.utils.UserStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(

    private val gson:Gson
) {
    private val dbRef:DatabaseReference = FirebaseDatabase.getInstance().getReference("chat_users")

//    init {
//        // Initialize dbRef with the desired document path
//    }
    // Authentication was successful, get the Firebase user ID
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private var userId = firebaseUser?.uid

    //private var currentUsername:String?=null
    private fun setUsername(username: String){

        this.userId = username
    }


//    fun login(username: String, password: String, done: (Boolean, String?) -> Unit) {
//        dbRef.addListenerForSingleValueEvent(object  : MyEventListener(){
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //if the current user exists
//                if (snapshot.hasChild(username)){
//                    //user exists , its time to check the password
//                    val dbPassword = snapshot.child(username).child(PASSWORD).value
//                    if (password == dbPassword) {
//                        //password is correct and sign in
//                        dbRef.child(username).child(STATUS).setValue(UserStatus.ONLINE)
//                            .addOnCompleteListener {
//                                setUsername(username)
//                                done(true,null)
//                            }.addOnFailureListener {
//                                done(false,"${it.message}")
//                            }
//                    }else{
//                        //password is wrong, notify user
//                        done(false,"Password is wrong")
//                    }
//
//                }else{
//                    //user doesnt exist, register the user
//                    dbRef.child(username).child(PASSWORD).setValue(password).addOnCompleteListener {
//                        dbRef.child(username).child(STATUS).setValue(UserStatus.ONLINE)
//                            .addOnCompleteListener {
//                                setUsername(username)
//                                done(true,null)
//                            }.addOnFailureListener {
//                                done(false,it.message)
//                            }
//                    }.addOnFailureListener {
//                        done(false,it.message)
//                    }
//
//                }
//            }
//        })
//    }

    fun observeUsersStatus(status: (List<Pair<String, String>>) -> Unit) {
        dbRef.addValueEventListener(object : MyEventListener() {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.children.filter { it.key !=userId }.map {
                    it.key!! to it.child(STATUS).value.toString()
                }
                status(list)
            }
        })
    }

    fun subscribeForLatestEvent(listener:Listener){
        try {
            dbRef.child(userId!!).child(LATEST_EVENT).addValueEventListener(
                object : MyEventListener() {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        super.onDataChange(snapshot)
                        val event = try {
                            gson.fromJson(snapshot.value.toString(),DataModel::class.java)
                        }catch (e:Exception){
                            e.printStackTrace()
                            null
                        }
                        event?.let {
                            listener.onLatestEventReceived(it)
                        }
                    }
                }
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun sendMessageToOtherClient(message:DataModel, success:(Boolean) -> Unit){
        val convertedMessage = gson.toJson(message.copy(sender = userId))
        dbRef.child(message.target).child(LATEST_EVENT).setValue(convertedMessage)
            .addOnCompleteListener {
                success(true)
            }.addOnFailureListener {
                success(false)
            }
    }

    fun changeMyStatus(status: UserStatus) {
        dbRef.child(userId!!).child(STATUS).setValue(status.name)
    }

    fun clearLatestEvent() {
        dbRef.child(userId!!).child(LATEST_EVENT).setValue(null)
    }

    fun logOff(function:()->Unit) {
        dbRef.child(userId!!).child(STATUS).setValue(UserStatus.OFFLINE)
            .addOnCompleteListener { function() }
    }


    interface Listener {
        fun onLatestEventReceived(event: DataModel)
    }
}