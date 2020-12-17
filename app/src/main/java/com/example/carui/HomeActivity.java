package com.example.carui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView weatherTextView, timeTextView, dateTextView;
    private ImageButton gpsBtn, radioBtn, phoneBtn, settingsBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        weatherTextView = findViewById(R.id.textview_weather);
        timeTextView = findViewById(R.id.textview_time);
        dateTextView = findViewById(R.id.textview_date);

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        int month = cal.get(Calendar.MONTH);
        double temp = (Math.pow((month - 6), 4))/(-25) + 35;
        DecimalFormat df = new DecimalFormat("##.#");
        weatherTextView.setText(df.format(temp) + " C");
        //weatherTextView.setText(month + " C");

        int h = cal.get(Calendar.HOUR_OF_DAY);
        String hours = h < 10 ? "0"+h : String.valueOf(h);
        int m = cal.get(Calendar.MINUTE);
        String minutes = m < 10 ? "0"+m : String.valueOf(m);
        timeTextView.setText(hours + ":" + minutes);

        int d = cal.get(Calendar.DAY_OF_MONTH);
        String day = d < 10 ? "0"+d : String.valueOf(d);
        dateTextView.setText(day + " " + (new DateFormatSymbols().getMonths()[month]).substring(0, 3).toUpperCase());

        gpsBtn = findViewById(R.id.home_gps_btn);
        radioBtn = findViewById(R.id.home_radio_btn);
        phoneBtn = findViewById(R.id.home_phone_btn);
        settingsBtn = findViewById(R.id.home_settings_btn);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == gpsBtn)
            startActivity(new Intent(HomeActivity.this, GPSActivity.class));
        else if (v == radioBtn)
            startActivity(new Intent(HomeActivity.this, RadioActivity.class));
        else if (v == phoneBtn)
            startActivity(new Intent(HomeActivity.this, PhoneActivity.class));
        else if (v == settingsBtn)
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
    }
}