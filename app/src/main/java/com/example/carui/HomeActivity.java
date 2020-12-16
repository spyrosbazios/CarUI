package com.example.carui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView weatherTextView, timeTextView, dateTextView;
    private ImageButton gpsBtn, radioBtn, phoneBtn, settingsBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        weatherTextView = findViewById(R.id.textview_weather);
        timeTextView = findViewById(R.id.textview_time);
        dateTextView = findViewById(R.id.textview_date);

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        timeTextView.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        dateTextView.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + (new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)]).substring(0, 3).toUpperCase());

        gpsBtn = findViewById(R.id.home_gps_btn);
        radioBtn = findViewById(R.id.home_radio_btn);
        phoneBtn = findViewById(R.id.home_phone_btn);
        settingsBtn = findViewById(R.id.home_settings_btn);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v == gpsBtn)
            startActivity(new Intent(this, GPSActivity.class));
        else if (v == radioBtn)
            startActivity(new Intent(this, RadioActivity.class));
        else if (v == phoneBtn)
            startActivity(new Intent(this, PhoneActivity.class));
        else if (v == settingsBtn)
            startActivity(new Intent(this, SettingsActivity.class));
    }
}
