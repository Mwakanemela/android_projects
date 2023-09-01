package com.example.zegocloudcall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainActivity extends AppCompatActivity {

    EditText userId;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = findViewById(R.id.usernameEt);
        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener((v)-> {
            if(userId.getText().toString().trim()== ""){
                return;
            }
            //start service
            startService(userId.getText().toString().trim());
            Intent intent = new Intent(MainActivity.this, CallActivity.class);
            intent.putExtra("userId", userId.getText().toString().trim());
            startActivity(intent);
        });
    }

    void startService(String userId) {
        Application application = getApplication(); // Android's application context
        long appID = 1304017 ;   // yourAppID
        String appSign = "b96e46e136f267f3f51f9c31a91130229ed348766bd89e088dd67fa1ce3d8e8e";  // yourAppSign
        String userID = userId; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName = "Mwaka";   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();//stop service
    }
}