package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener, UpdateUtilities{

    private ImageView musicLeftButton, musicRightButton;
    private ImageButton homeButton, playButton;
    private ListView contactListView;
    private String[] contactList = {"Spyros", "Christos", "Manos", "Nantia", "Ion Androutsopoulos", "Chalkidis", "Dio", "Chara", "George"};
    private SeekBar musicSeekBar;
    private boolean playState;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        musicLeftButton = (ImageView)findViewById(R.id.musicprevious_img);
        musicLeftButton.setOnClickListener(this);
        musicRightButton = (ImageView)findViewById(R.id.musicnext_img);
        musicRightButton.setOnClickListener(this);
        playButton = (ImageButton)findViewById(R.id.musicplay_btn);
        playButton.setOnClickListener(this);
        playState = Utilities.PLAY_STATE;
        if (playState)
            playButton.setBackgroundResource(R.drawable.holdcall);
        else
            playButton.setBackgroundResource(R.drawable.musicplay);

        musicSeekBar = (SeekBar)findViewById(R.id.musicbar_seekbar);
        musicSeekBar.setOnClickListener(this);
        musicSeekBar.setMax(100);
        musicSeekBar.setProgress(Utilities.MUSIC_PROGRESS);

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
                Utilities.CALLER = contactAdapter.getItem(position);
                Utilities.CALL_STATE = CallingActivity.CALLSTATE.RUNNING;
                updateUtilities();
                Utilities.startTimer();
                startActivity(new Intent(PhoneActivity.this, CallingActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        this.updateUtilities();
        startActivity(new Intent(PhoneActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == playButton) {
            playState = !playState;
            if (playState)
                playButton.setBackgroundResource(R.drawable.holdcall);
            else
                playButton.setBackgroundResource(R.drawable.musicplay);

        }
        else if (v == musicLeftButton)
            musicSeekBar.setProgress(Math.max(musicSeekBar.getProgress() - 10, 0));
        else if (v == musicRightButton)
            musicSeekBar.setProgress(Math.min(musicSeekBar.getProgress() + 10, musicSeekBar.getMax()));
    }

    @Override
    public void updateUtilities() {
        Utilities.PLAY_STATE = playState;
        Utilities.MUSIC_PROGRESS = musicSeekBar.getProgress();
    }
}