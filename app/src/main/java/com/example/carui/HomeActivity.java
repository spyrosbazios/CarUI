package com.example.carui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
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

        weatherTextView = (TextView)findViewById(R.id.textview_weather);
        timeTextView = (TextView)findViewById(R.id.textview_time);
        dateTextView = (TextView)findViewById(R.id.textview_date);

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

        gpsBtn = (ImageButton)findViewById(R.id.gps_btn);
        radioBtn = (ImageButton)findViewById(R.id.radio_btn);
        phoneBtn = (ImageButton)findViewById(R.id.phone_btn);
        settingsBtn = (ImageButton)findViewById(R.id.settings_btn);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onClick(View v) {
        if (v == gpsBtn)
            startActivity(new Intent(HomeActivity.this, GPSActivity.class));
        else if (v == radioBtn)
            startActivity(new Intent(HomeActivity.this, RadioActivity.class));
        else if (v == phoneBtn) {
            Intent intent = new Intent(HomeActivity.this, PhoneActivity.class);
            startActivity(intent);
        }
        else if (v == settingsBtn)
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
    }
}