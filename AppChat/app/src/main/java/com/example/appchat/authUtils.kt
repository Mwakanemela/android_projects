package com.example.appchat

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appchat.calling.repository.MainRepository
import com.example.appchat.calling.ui.CallActivity
import com.permissionx.guolindev.PermissionX

fun AppCompatActivity.getCameraAndMicPermission(
    context: Context,
    username: String,
    onSuccess: () -> Unit
) {
    val mainRepository = MainRepository.getInstance()
    PermissionX.init(this)
        .permissions(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        )
        .request { allGranted, _, _ ->
            if (allGranted) {
                mainRepository.login(username, context) {
                    onSuccess()
                }
            } else {
                Toast.makeText(this, "Camera and mic permission is required", Toast.LENGTH_SHORT)
                    .show()
            }
        }
}

fun AppCompatActivity.moveToCallActivity(context: Context) {
    val intent = Intent(context, CallActivity::class.java)
    startActivity(intent)
}
//
//
//fun AppCompatActivity.getCameraAndMicPermission(
//    context: Context,
//        username: String,
//        onSuccess: () -> Unit){
//    val mainRepository = MainRepository.getInstance()
//    PermissionX.init(this)
//
//        .permissions(android.Manifest.permission.CAMERA,android.Manifest.permission.RECORD_AUDIO)
//        .request{allGranted,_,_ ->
//
//            if (allGranted){
//                mainRepository.login(username, context) {
//                        onSuccess()
//                    }
//            } else{
//                Toast.makeText(this, "camera and mic permission is required", Toast.LENGTH_SHORT)
//                    .show()
//            }
//}
//
//    fun moveToCallActivity(context: Context) {
//        val intent = Intent(context, CallActivity::class.java)
//        context.startActivity(intent)
//    }
//}
//
////
////object AuthenticationUtil {
////
////    fun signInAndMoveToCallActivity(
////        context: Context,
////        username: String,
////        onSuccess: () -> Unit
////    ) {
////        val mainRepository = MainRepository.getInstance()
////        PermissionX.init(this)
////            .permissions(
////                android.Manifest.permission.CAMERA,
////                android.Manifest.permission.RECORD_AUDIO
////            )
////            .request { allGranted: Boolean, grantedList: List<String>, deniedList: List<String> ->
////                if (allGranted) {
////                    mainRepository.login(username, context) {
////                        onSuccess()
////                    }
////                } else {
////                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
////                }
////            }
////    }
//
