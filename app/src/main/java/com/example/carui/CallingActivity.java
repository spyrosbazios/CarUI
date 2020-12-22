package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallingActivity extends AppCompatActivity implements View.OnClickListener, UpdateUtilities{

    private ImageButton homeButton, leftBtn, rightBtn;
    private ImageView leftImageView, rightImageView;
    private TextView leftTextView, rightTextView, callerTextView, isCallingTextView;
    private CALLSTATE callState;
    private Thread t;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        leftBtn = (ImageButton)findViewById(R.id.left_btn);
        leftBtn.setOnClickListener(this);
        leftImageView = (ImageView)findViewById(R.id.left_img);
        leftTextView = (TextView)findViewById(R.id.left_textview);

        rightBtn = (ImageButton)findViewById(R.id.right_btn);
        rightBtn.setOnClickListener(this);
        rightImageView = (ImageView)findViewById(R.id.right_img);
        rightTextView = (TextView)findViewById(R.id.right_textview);

        callerTextView = (TextView)findViewById(R.id.callername_textview);
        isCallingTextView = (TextView)findViewById(R.id.iscalling_textview);

        callState = Utilities.CALL_STATE;

        t = new Thread(new Runnable() {
            @Override public void run() {
                while (Utilities.CALLER != null) {
                    while (!(callState == CALLSTATE.RUNNING));
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

        if (Utilities.CALLER != null) t.start();

        if (Utilities.CALLER != null) {
            callerTextView.setText(Utilities.CALLER);
            setLeftView(Utilities.CALL_STATE);
        }
        else {
            callerTextView.setText("Nantia");
            setLeftView(CALLSTATE.ANSWER);
        }
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        t.interrupt();
        this.updateUtilities();
        startActivity(new Intent(CallingActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == leftBtn) {
            if (Utilities.CALLER != null) {
                if (callState == CALLSTATE.RUNNING) setLeftView(CALLSTATE.HOLD);
                else setLeftView(CALLSTATE.RUNNING);
            }
            else {
                Utilities.CALLER = "Nantia";
                Utilities.startTimer();
                t.start();
                setLeftView(CALLSTATE.RUNNING);
            }
        }
        else if (v == rightBtn) {
            Utilities.CALLER = null;
            try {Thread.sleep(100);}
            catch (InterruptedException e) {e.printStackTrace();}
            startActivity(new Intent(CallingActivity.this, PhoneActivity.class));
        }
    }

    @Override
    public void updateUtilities() {
        Utilities.CALL_STATE = callState;
    }

    @SuppressLint("SetTextI18n")
    private void setLeftView(CALLSTATE lv) {
        switch (lv) {
            case ANSWER:
                callState = CALLSTATE.ANSWER;
                leftImageView.setImageResource(R.drawable.answercall);
                leftTextView.setText("Answer");
                isCallingTextView.setText("Calling ...");
                rightTextView.setText("Decline");
                break;
            case HOLD:
                callState = CALLSTATE.HOLD;
                leftImageView.setImageResource(R.drawable.holdcall);
                leftTextView.setText("Resume");
                isCallingTextView.setText("On Hold");
                rightTextView.setText("Hang Up");
                break;
            case RUNNING:
                callState = CALLSTATE.RUNNING;
                leftImageView.setImageResource(R.drawable.holdcall);
                leftTextView.setText("Hold");
                rightTextView.setText("Hang Up");
                break;
            default:
                break;
        }
    }

    public enum CALLSTATE {
        ANSWER, HOLD, RUNNING
    };
}