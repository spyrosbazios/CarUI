package com.example.carui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RadioActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private int i = 0;
    private ImageButton homeButton, previousButton, nextButton;
    private Button radioButton;
    private TextView[][]  stationTextView= new TextView[4][2];
    private String[] stationArray = {"88.9", "89.8", "91.6", "92.3", "94.6", "97.5", "97.8", "101.3", "106.2", "107.0"};
    private ArrayList<String> favourites = new ArrayList<>();
    //ArrayList<String> stationList = new ArrayList<>(Arrays.asList(stationArray));

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

        int ri;
        Random r = new Random();
        radioButton.setText(stationArray[r.nextInt(stationArray.length)]);
        for (int i = 0; i < 4; i++) {
            do {
                ri = r.nextInt(stationArray.length);
            }
            while (favourites.contains(stationArray[ri]));
            favourites.add(stationArray[ri]);
        }
        stationTextView[1][0].setText(favourites.get(0));
        stationTextView[2][0].setText(favourites.get(1));
        stationTextView[3][0].setText(favourites.get(2));
        stationTextView[1][1].setText(favourites.get(3));
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RadioActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == previousButton || v == nextButton) {
            if (v == previousButton) i--;
            else i++;
            radioButton.setText(stationArray[Utilities.clampIntToLimits(i, 0, stationArray.length-1)]);
        } else {
            outerloop: for (int i = 1; i < 4; i ++) {
                for (int j = 0; j < 2; j++) {
                    if (v == stationTextView[i][j]) {
                        if (((Button)v).getText().equals("+")) {
                            if (!favourites.contains(String.valueOf(radioButton.getText()))) {
                                ((Button) v).setText(radioButton.getText());
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