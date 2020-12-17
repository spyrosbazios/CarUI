package com.example.carui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton homeButton, playButton;
    private ListView contactListView;
    private String[] contactList = {"Spyros", "Christos", "Manos", "Nantia", "Ion Androutsopoulos", "Chalkidis", "Dio", "Chara"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        playButton = (ImageButton)findViewById(R.id.musicplay_btn);

        contactListView = (ListView)findViewById(R.id.contacts_listview);
        ArrayAdapter<String> contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView)view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        contactListView.setAdapter(contactAdapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            startActivity(new Intent(PhoneActivity.this, HomeActivity.class));
        else if (v == playButton) {
            //playButton.setBackgroundResource(R.drawable.musicpause);
        }
    }
}
