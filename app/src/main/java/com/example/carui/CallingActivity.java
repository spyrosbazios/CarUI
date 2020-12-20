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
    private boolean updateUI = true;
    private boolean holdState = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

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

        if (Utilities.CALLER != null) {
            callerTextView.setText(Utilities.CALLER);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (updateUI) {
                        while(holdState);
                        runOnUiThread(new Runnable() {
                            @Override
                            @SuppressLint({"SetText|18n", "SetTextI18n"})
                            public void run() {
                                int m = (int) ((Utilities.getCallDuration() / (1000 * 60)) % 60);
                                int s = (int) (Utilities.getCallDuration() / 1000) % 60;
                                isCallingTextView.setText(Utilities.addZeroInBeginning(m) + ":" + Utilities.addZeroInBeginning(s));
                            }
                        });
                        try {Thread.sleep(1000);}
                        catch (InterruptedException e) {e.printStackTrace();}
                    }
                }
            });
            t.start();
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
    public void onBackPressed() {
        updateUI = false;
        startActivity(new Intent(CallingActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == declineBtn) {
            Utilities.CALLER = null;
            startActivity(new Intent(CallingActivity.this, PhoneActivity.class));
        }
        else if (v == answerBtn) {
            if (Utilities.CALLER != null) {
                holdState = !holdState;
                if (holdState) {
                    isCallingTextView.setText("On Hold");
                    holdTextView.setText("Resume");
                }
                else holdTextView.setText("Hold");
            }
            else setAnswerVisibility(false);
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