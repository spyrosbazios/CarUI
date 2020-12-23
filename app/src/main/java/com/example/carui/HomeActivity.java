package com.example.carui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView weatherTextView, timeTextView, dateTextView;
    private ImageButton gpsButton, radioButton, phoneButton, settingsButton;
    private ImageView weatherImageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        weatherTextView = (TextView)findViewById(R.id.textview_weather);
        weatherTextView.setText(Utilities.calcWeather());
        weatherImageView = (ImageView)findViewById(R.id.weather_img);
        weatherImageView.setImageResource(Utilities.calcWeather().compareToIgnoreCase("25") < 0 ? R.drawable.weathercloud : R.drawable.weathersun);
        timeTextView = (TextView)findViewById(R.id.textview_time);
        timeTextView.setText(Utilities.calcTime());
        dateTextView = (TextView)findViewById(R.id.textview_date);
        dateTextView.setText(Utilities.calcDate());

        gpsButton = (ImageButton)findViewById(R.id.gps_btn);
        gpsButton.setOnClickListener(this);
        radioButton = (ImageButton)findViewById(R.id.radio_btn);
        radioButton.setOnClickListener(this);
        phoneButton = (ImageButton)findViewById(R.id.phone_btn);
        phoneButton.setOnClickListener(this);
        settingsButton = (ImageButton)findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == gpsButton)
            startActivity(new Intent(HomeActivity.this, GPSActivity.class));
        else if (v == radioButton)
            startActivity(new Intent(HomeActivity.this, RadioActivity.class));
        else if (v == phoneButton) {
            if (Utilities.CALLER == null)
                startActivity(new Intent(HomeActivity.this, PhoneActivity.class));
            else
                startActivity(new Intent(HomeActivity.this, CallingActivity.class));
        }
        else if (v == settingsButton)
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
    }
}