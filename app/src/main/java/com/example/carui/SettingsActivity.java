package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton homeButton;

    private ImageView brightnessLeftButton, brightnessRightButton;
    private SeekBar brightnessSeekBar;
    private boolean autoState;
    private Button autoOnButton, autoOffButton;

    private int l;
    private ImageView languageUpImageView, languageDownImageView;
    private TextView languageMiddleImageView;
    private final String[] languageList = {"Arabic", "Chinese", "English", "French", "German", "Japanese", "Portuguese", "Russian", "Spanish"};

    private int day, month, year, hour, minute;
    private ImageView dayUpButton, monthUpButton, yearUpButton;
    private TextView dayTextView, monthTextView, yearTextView;
    private ImageView dayDownButton, monthDownButton, yearDownButton;
    private ImageView hourUpButton, minuteUpButton;
    private TextView hourTextView, minuteTextView;
    private ImageView hourDownButton, minuteDownButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        initializeBrightnessViews();
        initializeLanguageViews();
        initializeDateTimeViews();
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        Utilities.BRIGHTNESS_PROGRESS = brightnessSeekBar.getProgress();
        Utilities.AUTOSTATE = autoState;
        Utilities.LANGUAGE = l;
        Utilities.DAY = day;
        Utilities.MONTH = month;
        Utilities.YEAR = year;
        Utilities.HOUR = hour;
        Utilities.MINUTE = minute;
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();

        else if (v == languageUpImageView || v == languageDownImageView) {
            if (v == languageUpImageView) l--;
            else l++;
            languageMiddleImageView.setText(languageList[Utilities.clampIntToLimits(l, 0, languageList.length-1)]);
        }
        else if (v == brightnessLeftButton)
            brightnessSeekBar.setProgress(brightnessSeekBar.getProgress() - 10 >= 0 ? brightnessSeekBar.getProgress() - 10 : 0);
        else if (v == brightnessRightButton)
            brightnessSeekBar.setProgress(brightnessSeekBar.getProgress() + 10 <= 100 ? brightnessSeekBar.getProgress() + 10 : brightnessSeekBar.getMax());

        handleDateTimeViews(v);

        if (v == autoOnButton) autoState = true;
        else if (v == autoOffButton) autoState = false;
        updateAutoState();
    }

    private void initializeBrightnessViews() {
        brightnessLeftButton = (ImageView)findViewById(R.id.brightnessleft_btn);
        brightnessLeftButton.setOnClickListener(this);
        brightnessSeekBar = (SeekBar)findViewById(R.id.brightnessbar_seekbar);
        brightnessSeekBar.setOnClickListener(this);
        brightnessRightButton = (ImageView)findViewById(R.id.brightnessright_btn);
        brightnessRightButton.setOnClickListener(this);
        autoOnButton = (Button)findViewById(R.id.autoon_btn);
        autoOnButton.setOnClickListener(this);
        autoOffButton = (Button)findViewById(R.id.autooff_btn);
        autoOffButton.setOnClickListener(this);

        autoState = Utilities.AUTOSTATE;
        brightnessSeekBar.setMax(100);
        brightnessSeekBar.setProgress(Utilities.BRIGHTNESS_PROGRESS);
        updateAutoState();
    }

    private void initializeLanguageViews() {
        languageUpImageView = (ImageView)findViewById(R.id.languageup_btn);
        languageUpImageView.setOnClickListener(this);
        languageMiddleImageView = (TextView)findViewById(R.id.languagemiddle_textview);
        languageDownImageView = (ImageView)findViewById(R.id.languagedown_btn);
        languageDownImageView.setOnClickListener(this);

        l = Utilities.LANGUAGE;
        languageMiddleImageView.setText(languageList[l]);
    }

    private void initializeDateTimeViews() {
        dayUpButton = (ImageView)findViewById(R.id.dayup_btn);
        dayUpButton.setOnClickListener(this);
        monthUpButton = (ImageView)findViewById(R.id.monthup_btn);
        monthUpButton.setOnClickListener(this);
        yearUpButton = (ImageView)findViewById(R.id.yearup_btn);
        yearUpButton.setOnClickListener(this);

        dayTextView = (TextView)findViewById(R.id.day_textview);
        monthTextView = (TextView)findViewById(R.id.month_textview);
        yearTextView = (TextView)findViewById(R.id.year_textview);

        dayDownButton = (ImageView)findViewById(R.id.daydown_btn);
        dayDownButton.setOnClickListener(this);
        monthDownButton = (ImageView)findViewById(R.id.monthdown_btn);
        monthDownButton.setOnClickListener(this);
        yearDownButton = (ImageView)findViewById(R.id.yeardown_btn);
        yearDownButton.setOnClickListener(this);

        hourTextView = (TextView)findViewById(R.id.hour_textview);
        minuteTextView = (TextView)findViewById(R.id.minute_textview);

        hourUpButton = (ImageView)findViewById(R.id.hourup_btn);
        hourUpButton.setOnClickListener(this);
        minuteUpButton = (ImageView)findViewById(R.id.minuteup_btn);
        minuteUpButton.setOnClickListener(this);

        hourDownButton = (ImageView)findViewById(R.id.hourdown_btn);
        hourDownButton.setOnClickListener(this);
        minuteDownButton = (ImageView)findViewById(R.id.minutedown_btn);
        minuteDownButton.setOnClickListener(this);

        day = Utilities.DAY;
        month = Utilities.MONTH;
        year = Utilities.YEAR;
        hour = Utilities.HOUR;
        minute = Utilities.MINUTE;
        updateDateTimeText();
    }

    private void handleDateTimeViews(View v) {
        if (v == dayUpButton) day++;
        else if (v == monthUpButton) month++;
        else if (v == yearUpButton) year++;

        else if (v == dayDownButton) day--;
        else if (v == monthDownButton) month--;
        else if (v == yearDownButton) year--;

        else if (v == hourUpButton) hour++;
        else if (v == minuteUpButton) minute++;

        else if (v == hourDownButton) hour--;
        else if (v == minuteDownButton) minute--;

        year = Utilities.clampIntToLimits(year, 1970, 2030);
        month = Utilities.clampIntToLimits(month, 0, 11);
        month++;
        if (month != 2) {
            if (month <= 7 && month%2 == 1) day = Utilities.clampIntToLimits(day, 1, 31);
            else if (month <= 7 && month%2 == 0) day = Utilities.clampIntToLimits(day, 1, 30);
            else if (month > 7 && month%2 == 1) day = Utilities.clampIntToLimits(day, 1, 30);
            else if (month > 7 && month%2 == 0) day = Utilities.clampIntToLimits(day, 1, 31);
        } else day = Utilities.clampIntToLimits(day, 1, 28);
        month--;

        hour = Utilities.clampIntToLimits(hour, 0, 23);
        minute = Utilities.clampIntToLimits(minute, 0, 59);
        updateDateTimeText();
    }

    private void updateDateTimeText() {
        dayTextView.setText(Utilities.addZeroInBeginning(day));
        monthTextView.setText((new DateFormatSymbols().getMonths()[month]).substring(0, 3).toUpperCase());
        yearTextView.setText(String.valueOf(year));
        hourTextView.setText(Utilities.addZeroInBeginning(hour));
        minuteTextView.setText(Utilities.addZeroInBeginning(minute));
    }

    @SuppressLint("ResourceType")
    private void updateAutoState() {
        if (autoState) {
            autoOnButton.setBackgroundResource(R.color.white);
            autoOnButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, R.color.teal));
            autoOffButton.setBackgroundResource(R.layout.border_rectangle);
            autoOffButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, R.color.white));
        } else {
            autoOffButton.setBackgroundResource(R.color.white);
            autoOffButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, R.color.teal));
            autoOnButton.setBackgroundResource(R.layout.border_rectangle);
            autoOnButton.setTextColor(ContextCompat.getColor(SettingsActivity.this, R.color.white));
        }
    }
}