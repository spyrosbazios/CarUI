package com.example.carui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RadioActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, UpdateUtilities{

    private int rl;
    private ImageButton homeButton, previousButton, nextButton;
    private Button radioButton;
    private TextView[][] stationButton = new TextView[4][2];
    private String[] stationArray = {"88.9", "89.8", "91.6", "92.3", "94.6", "97.5", "97.8", "101.3", "106.2", "107.0"};
    private ArrayList<String> favourites = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        radioButton = (Button)findViewById(R.id.radio_btn);
        radioButton.setOnClickListener(this);
        radioButton.setOnLongClickListener(this);
        previousButton = (ImageButton)findViewById(R.id.prev_btn);
        previousButton.setOnClickListener(this);
        nextButton = (ImageButton)findViewById(R.id.next_btn);
        nextButton.setOnClickListener(this);

        stationButton[0][0] = (Button)findViewById(R.id.leftstation1_textview);
        stationButton[1][0] = (Button)findViewById(R.id.leftstation2_textview);
        stationButton[2][0] = (Button)findViewById(R.id.leftstation3_textview);
        stationButton[3][0] = (Button)findViewById(R.id.leftstation4_textview);
        stationButton[0][1] = (Button)findViewById(R.id.rightstation1_textview);
        stationButton[1][1] = (Button)findViewById(R.id.rightstation2_textview);
        stationButton[2][1] = (Button)findViewById(R.id.rightstation3_textview);
        stationButton[3][1] = (Button)findViewById(R.id.rightstation4_textview);
        for (int i = 1; i < 4; i ++) {
            for (int j = 0; j < 2; j++) {
                stationButton[i][j].setOnClickListener(this);
                stationButton[i][j].setOnLongClickListener(this);
            }
        }

        stationButton[0][0].setText(Utilities.calcWeather());
        stationButton[0][1].setText(Utilities.calcTime());

        rl = Utilities.RADIO_LIVE;
        radioButton.setText(stationArray[rl]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                stationButton[i+1][j].setText(Utilities.STATION_FAVOURITES[i][j]);
                if (!Utilities.STATION_FAVOURITES[i][j].equals("ADD"))
                    favourites.add(Utilities.STATION_FAVOURITES[i][j]);
            }
        }
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        this.updateUtilities();
        startActivity(new Intent(RadioActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == previousButton || v == nextButton) {
            if (v == previousButton) rl--;
            else rl++;
            radioButton.setText(stationArray[Utilities.clampIntToLimits(rl, 0, stationArray.length-1)]);
        } else {
            outerloop: for (int i = 1; i < 4; i ++) {
                for (int j = 0; j < 2; j++) {
                    if (v == stationButton[i][j]) {
                        if (((Button)v).getText().equals("ADD")) {
                            if (!favourites.contains(String.valueOf(radioButton.getText()))) {
                                ((Button)v).setText(radioButton.getText());
                                favourites.add(String.valueOf(radioButton.getText()));
                                Toast.makeText(RadioActivity.this, radioButton.getText() + " added in favourites", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(RadioActivity.this, radioButton.getText() + " is already in favourites", Toast.LENGTH_SHORT).show();
                        }
                        else radioButton.setText(((Button)v).getText());
                        break outerloop;
                    }
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        outerloop: for (int i = 1; i < 4; i ++) {
            for (int j = 0; j < 2; j++) {
                if (v == stationButton[i][j]) {
                    if (!(((Button)v).getText().equals("ADD"))) {
                        favourites.remove(((Button)v).getText());
                        Toast.makeText(RadioActivity.this, ((Button)v).getText() + " removed from favourites", Toast.LENGTH_SHORT).show();
                        ((Button)v).setText("ADD");
                    }
                    break outerloop;
                }
            }
        }
        return true;
    }

    @Override
    public void updateUtilities() {
        for (int i = 0; i < stationArray.length; i++) {
            if (stationArray[i].equals(radioButton.getText()))
                Utilities.RADIO_LIVE = i;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Utilities.STATION_FAVOURITES[i][j] = String.valueOf(stationButton[i+1][j].getText());
            }
        }
    }
}