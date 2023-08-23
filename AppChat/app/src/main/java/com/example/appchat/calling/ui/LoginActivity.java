//package com.example.appchat.calling.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.appchat.calling.repository.MainRepository;
//import com.example.appchat.databinding.ActivityLogin4Binding;
//import com.permissionx.guolindev.PermissionX;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private ActivityLogin4Binding views;
//
//    private MainRepository mainRepository;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        views = ActivityLogin4Binding.inflate(getLayoutInflater());
//        setContentView(views.getRoot());
//        init();
//    }
//
//    private void init() {
//        mainRepository = MainRepository.getInstance();
//        views.enterBtn.setOnClickListener(v -> {
//            PermissionX.init(this)
//                    .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
//                    .request((allGranted, grantedList, deniedList) -> {
//                        if (allGranted) {
//                            //login to firebase here
//
//                            mainRepository.login(
//                                    views.username.getText().toString(), getApplicationContext(), () -> {
//                                        //if success then we want to move to call activity
//                                        startActivity(new Intent(LoginActivity.this, CallActivity.class));
//                                    }
//                            );
//                        }
//                    });
//
//
//        });
//    }
//}