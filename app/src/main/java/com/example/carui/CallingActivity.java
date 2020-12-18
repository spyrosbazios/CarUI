package com.example.carui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallingActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton homeButton, answerBtn, declineBtn;
    private TextView answerTextView, declineTextView;

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

        answerTextView = (TextView)findViewById(R.id.answer_textview);
        declineTextView = (TextView)findViewById(R.id.decline_textview);
        answerTextView.setTextColor(Color.WHITE);
        declineTextView.setTextColor(Color.WHITE);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            startActivity(new Intent(CallingActivity.this, HomeActivity.class));
        else if (v == answerBtn)
            Toast.makeText(CallingActivity.this, "Phone Answered!", Toast.LENGTH_SHORT).show();
        else if (v == declineBtn)
            Toast.makeText(CallingActivity.this, "Phone Declined!", Toast.LENGTH_SHORT).show();
    }
}
