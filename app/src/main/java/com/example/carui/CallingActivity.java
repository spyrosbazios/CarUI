package com.example.carui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton homeButton, answerBtn, declineBtn;
    private TextView answerTextView, declineTextView, callerTextView, isCallingTextView;
    private String caller = "Nantia";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);
        answerBtn = (ImageButton)findViewById(R.id.answer_btn);
        answerBtn.setOnClickListener(this);
        declineBtn = (ImageButton)findViewById(R.id.decline_btn);
        declineBtn.setOnClickListener(this);

        callerTextView = (TextView)findViewById(R.id.callername_textview);
        callerTextView.setTextColor(Color.WHITE);
        callerTextView.setText(caller);
        isCallingTextView = (TextView)findViewById(R.id.iscalling_textview);
        isCallingTextView.setTextColor(Color.WHITE);
        answerTextView = (TextView)findViewById(R.id.answer_textview);
        answerTextView.setTextColor(Color.WHITE);
        declineTextView = (TextView)findViewById(R.id.decline_textview);
        declineTextView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == homeButton || v == declineBtn)
            startActivity(new Intent(CallingActivity.this, HomeActivity.class));
        else if (v == answerBtn) {
            Intent intent = new Intent(CallingActivity.this, PhoneActivity.class);
            intent.putExtra("Caller", caller);
            startActivity(intent);
        }
    }
}
