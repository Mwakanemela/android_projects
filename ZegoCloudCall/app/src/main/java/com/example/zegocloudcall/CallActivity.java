package com.example.zegocloudcall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class CallActivity extends AppCompatActivity {

    EditText targetEt;
    ZegoSendCallInvitationButton videoCallBtn, audioCallBtn;
    TextView userTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        targetEt = findViewById(R.id.targetEt);
        userTv = findViewById(R.id.userTv);
        videoCallBtn = findViewById(R.id.videoCallBtn);
        audioCallBtn = findViewById(R.id.voiceCallBtn);

        String uid = getIntent().getStringExtra("userId");
        userTv.setText("hey "+uid);

        targetEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
String targetId = targetEt.getText().toString().trim();
voiceCall(targetId);
videoCall(targetId);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    void voiceCall(String targetUserID){
        audioCallBtn.setIsVideoCall(false);
        audioCallBtn.setResourceID("zego_uikit_call");
        audioCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID,"targetUserName")));
    }
    void videoCall(String targetUserID){
        videoCallBtn.setIsVideoCall(true);
        videoCallBtn.setResourceID("zego_uikit_call");
        videoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID,"targetUserName")));
    }

}