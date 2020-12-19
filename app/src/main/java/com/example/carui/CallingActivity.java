package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

public class CallingActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout answerLayout, holdLayout;
    private ImageButton homeButton, answerBtn, declineBtn;
    private TextView answerTextView, holdTextView, declineTextView, callerTextView, isCallingTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        answerLayout = (LinearLayout)findViewById(R.id.linearlayout_answercall);
        holdLayout = (LinearLayout)findViewById(R.id.linearlayout_holdcall);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        answerBtn = (ImageButton)findViewById(R.id.answer_btn);
        answerBtn.setOnClickListener(this);
        answerTextView = (TextView)findViewById(R.id.answer_textview);
        holdTextView = (TextView)findViewById(R.id.hold_textview);

        declineBtn = (ImageButton)findViewById(R.id.decline_btn);
        declineBtn.setOnClickListener(this);
        declineTextView = (TextView)findViewById(R.id.decline_textview);

        callerTextView = (TextView)findViewById(R.id.callername_textview);
        isCallingTextView = (TextView)findViewById(R.id.iscalling_textview);

        String callerIntent = getIntent().getStringExtra("Caller");
        if (callerIntent != null) {
            callerTextView.setText(callerIntent);
            setAnswerVisibility(false);
        }
        else {
            callerTextView.setText("Nantia");
            setAnswerVisibility(true);
        }
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == homeButton || v == declineBtn)
            startActivity(new Intent(CallingActivity.this, HomeActivity.class));
        else if (v == answerBtn) {
            setAnswerVisibility(false);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setAnswerVisibility(boolean flag) {
        if (flag) {
            for (int i = 0; i < holdLayout.getChildCount(); i++)
                holdLayout.getChildAt(i).setVisibility(View.GONE);
            for (int i = 0; i < answerLayout.getChildCount(); i++)
                answerLayout.getChildAt(i).setVisibility(View.VISIBLE);
        }
        else {
            for (int i = 0; i < answerLayout.getChildCount(); i++)
                answerLayout.getChildAt(i).setVisibility(View.GONE);
            for (int i = 0; i < holdLayout.getChildCount(); i++)
                holdLayout.getChildAt(i).setVisibility(View.VISIBLE);
            isCallingTextView.setText("00:00");
            declineTextView.setText("Hang Up");
        }
    }
}