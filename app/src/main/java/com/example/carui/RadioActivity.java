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

public class RadioActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private int rl;
    private ImageButton homeButton, previousButton, nextButton;
    private Button radioButton;
    private TextView[][]  stationTextView = new TextView[4][2];
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

        stationTextView[0][0] = (Button)findViewById(R.id.leftstation1_textview);
        stationTextView[1][0] = (Button)findViewById(R.id.leftstation2_textview);
        stationTextView[2][0] = (Button)findViewById(R.id.leftstation3_textview);
        stationTextView[3][0] = (Button)findViewById(R.id.leftstation4_textview);
        stationTextView[0][1] = (Button)findViewById(R.id.rightstation1_textview);
        stationTextView[1][1] = (Button)findViewById(R.id.rightstation2_textview);
        stationTextView[2][1] = (Button)findViewById(R.id.rightstation3_textview);
        stationTextView[3][1] = (Button)findViewById(R.id.rightstation4_textview);
        for (int i = 1; i < 4; i ++) {
            for (int j = 0; j < 2; j++) {
                stationTextView[i][j].setOnClickListener(this);
                stationTextView[i][j].setOnLongClickListener(this);
            }
        }

        stationTextView[0][0].setText(Utilities.calcWeather());
        stationTextView[0][1].setText(Utilities.calcTime());

        rl = Utilities.RADIOLIVE;
        radioButton.setText(stationArray[rl]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                stationTextView[i+1][j].setText(Utilities.FAVOURITES[i][j]);
                if (!Utilities.FAVOURITES[i][j].equals("+"))
                    favourites.add(Utilities.FAVOURITES[i][j]);
            }
        }

        /*if (favourites.size() > 0) {
            for (int i = 0; i < favourites.size(); i++) {
                if (i == 0) stationTextView[1][0].setText(favourites.get(0) == null ? "+" : favourites.get(0));
                if (i == 1) stationTextView[2][0].setText(favourites.get(1) == null ? "+" : favourites.get(1));
                if (i == 2) stationTextView[3][0].setText(favourites.get(2) == null ? "+" : favourites.get(2));
                if (i == 3) stationTextView[1][1].setText(favourites.get(3) == null ? "+" : favourites.get(3));
                if (i == 4) stationTextView[2][1].setText(favourites.get(4) == null ? "+" : favourites.get(4));
                if (i == 5) stationTextView[3][1].setText(favourites.get(5) == null ? "+" : favourites.get(5));
            }
        }*/
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        for (int i = 0; i < stationArray.length; i++) {
            if (stationArray[i].equals(radioButton.getText()))
                Utilities.RADIOLIVE = i;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                Utilities.FAVOURITES[i][j] = String.valueOf(stationTextView[i+1][j].getText());
            }
        }
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
                    if (v == stationTextView[i][j]) {
                        if (((Button)v).getText().equals("+")) {
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
                if (v == stationTextView[i][j]) {
                    if (!(((Button)v).getText().equals("+"))) {
                        favourites.remove(((Button)v).getText());
                        ((Button)v).setText("+");
                        Toast.makeText(RadioActivity.this, radioButton.getText() + " removed from favourites", Toast.LENGTH_SHORT).show();
                    }
                    break outerloop;
                }
            }
        }
        return true;
    }
}