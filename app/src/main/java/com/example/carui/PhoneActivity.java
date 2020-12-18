package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener{

    private View layoutContacts, layoutPhonecall;
    private ImageButton homeButton, hangupButton, playButton;
    private ListView contactListView;
    private String[] contactList = {"Spyros", "Christos", "Manos", "Nantia", "Ion Androutsopoulos", "Chalkidis", "Dio", "Chara"};
    private TextView callerName, callDuration;
    private long startTime;
    //private Handler timerHandler;
    //private Runnable timerRunnable;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        layoutContacts = (LinearLayout)findViewById(R.id.linearlayout_contacts);
        layoutPhonecall = (LinearLayout)findViewById(R.id.linearlayout_phonecall);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);
        playButton = (ImageButton)findViewById(R.id.musicplay_btn);
        playButton.setOnClickListener(this);
        hangupButton = (ImageButton)findViewById(R.id.hangup_btn);
        hangupButton.setOnClickListener(this);

        callerName = (TextView)findViewById(R.id.callername_textview);
        callerName.setTextColor(Color.WHITE);
        callDuration = (TextView)findViewById(R.id.callduration_textview);
        callDuration.setTextColor(Color.WHITE);

        String caller = getIntent().getStringExtra("Caller");
        if (caller == null)
            setPhonecallVisibility(false);
        else {
            callerName.setText(caller);
            callDuration.setText("00:00");
            setPhonecallVisibility(true);
        }

        contactListView = (ListView)findViewById(R.id.contacts_listview);
        final ArrayAdapter<String> contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView)view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        contactListView.setAdapter(contactAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPhonecallVisibility(true);
                callerName.setText(contactAdapter.getItem(position));
                callDuration.setText("Calling...");
                /*startTime = System.currentTimeMillis();
                timerHandler = new Handler();
                timerRunnable = new Runnable() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void run() {
                        long millis = System.currentTimeMillis() - startTime;
                        int seconds = (int) (millis / 1000);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        callDuration.setText(String.format("%d:%02d", minutes, seconds));
                        timerHandler.postDelayed(timerRunnable, 1000);
                    }
                };*/
            }
        });
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            startActivity(new Intent(PhoneActivity.this, HomeActivity.class));
        else if (v == hangupButton) {
            //timerHandler.removeCallbacks(timerRunnable);
            setPhonecallVisibility(false);
        }
        else if (v == playButton) {
            //playButton.setBackgroundResource(R.drawable.musicpause);
        }
    }

    private void setPhonecallVisibility(boolean flag) {
        if (flag) {
            for (int i = 0; i < ((ViewGroup)layoutContacts).getChildCount(); i++)
                ((ViewGroup)layoutContacts).getChildAt(i).setVisibility(View.GONE);
            for (int i = 0; i < ((ViewGroup)layoutPhonecall).getChildCount(); i++)
                ((ViewGroup)layoutPhonecall).getChildAt(i).setVisibility(View.VISIBLE);
        }
        else {
            for (int i = 0; i < ((ViewGroup)layoutPhonecall).getChildCount(); i++)
                ((ViewGroup)layoutPhonecall).getChildAt(i).setVisibility(View.GONE);
            for (int i = 0; i < ((ViewGroup)layoutContacts).getChildCount(); i++)
                ((ViewGroup)layoutContacts).getChildAt(i).setVisibility(View.VISIBLE);
        }
    }
}