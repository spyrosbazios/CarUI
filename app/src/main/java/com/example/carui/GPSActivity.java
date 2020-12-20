package com.example.carui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GPSActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private ImageButton homeButton, moreButton, searchAddressButton;
    private LinearLayout favouritesPanel, newFavouritePanel;
    private boolean moreState = false;
    private Button[] favourites = new Button[5];
    private EditText searchAddressEditText, newLocationEditText, newAddressEditText;
    private Button newCancelButton, newAddButton;
    private int newFavouritePos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);
        moreButton = (ImageButton)findViewById(R.id.more_btn);
        moreButton.setOnClickListener(this);
        searchAddressButton = (ImageButton)findViewById(R.id.search_btn);
        searchAddressButton.setOnClickListener(this);
        favouritesPanel = (LinearLayout)findViewById(R.id.linearlayout_favourites);
        setPanelVisibility(favouritesPanel, false);
        newFavouritePanel = (LinearLayout)findViewById(R.id.linearlayout_newfavourite);
        setPanelVisibility(newFavouritePanel, false);

        favourites[0] = (Button)findViewById(R.id.favourites1_btn);
        favourites[1] = (Button)findViewById(R.id.favourites2_btn);
        favourites[2] = (Button)findViewById(R.id.favourites3_btn);
        favourites[3] = (Button)findViewById(R.id.favourites4_btn);
        favourites[4] = (Button)findViewById(R.id.favourites5_btn);
        for (int i = 0; i < favourites.length; i++) {
            favourites[i].setText(Utilities.GPS_FAVOURITES[i]);
            favourites[i].setOnClickListener(this);
            favourites[i].setOnLongClickListener(this);
        }

        searchAddressEditText = (EditText)findViewById(R.id.searchaddress_edittext);
        searchAddressEditText.setOnClickListener(this);
        newLocationEditText = (EditText)findViewById(R.id.newloc_edittext);
        searchAddressEditText.setOnClickListener(this);
        newAddressEditText = (EditText)findViewById(R.id.newaddress_edittext);
        searchAddressEditText.setOnClickListener(this);

        newCancelButton = (Button)findViewById(R.id.newcancel_btn);
        newCancelButton.setOnClickListener(this);
        newAddButton = (Button)findViewById(R.id.newadd_btn);
        newAddButton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        for (int i = 0; i < favourites.length; i++)
            Utilities.GPS_FAVOURITES[i] = String.valueOf(favourites[i].getText());
        startActivity(new Intent(GPSActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == moreButton) {
            moreState = !moreState;
            setPanelVisibility(favouritesPanel, moreState);
        }
        else if (v == searchAddressButton) {
            Toast.makeText(GPSActivity.this, "Navigating to " + searchAddressEditText.getText(), Toast.LENGTH_SHORT).show();
        }
        else if (v == newCancelButton || v == newAddButton) {
            setPanelVisibility(newFavouritePanel, false);
            if (v == newAddButton && newLocationEditText.getText().length() > 0 && newAddressEditText.getText().length() > 0) {
                favourites[newFavouritePos].setText(String.valueOf(newLocationEditText.getText() + ": " + newAddressEditText.getText()));
            }
        }

        for (int i = 0; i < favourites.length; i++) {
            if (v == favourites[i]) {
                if (favourites[i].getText().equals("ADD")) {
                    setPanelVisibility(newFavouritePanel, true);
                    newFavouritePos = i;
                }
                else {
                    Toast.makeText(GPSActivity.this, "Navigating to " + favourites[i].getText(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onLongClick(View v) {
        for (Button b: favourites) {
            if (v == b) {
                if (!(b.getText().equals("ADD"))) {
                    Toast.makeText(GPSActivity.this, b.getText() + " removed from favourites", Toast.LENGTH_SHORT).show();
                    b.setText("ADD");
                }
            }
        }
        return true;
    }

    private void setPanelVisibility(LinearLayout panel, boolean flag) {
        int state = flag ? View.VISIBLE : View.GONE;
        for (int i = 0; i < panel.getChildCount(); i++) {
            panel.getChildAt(i).setVisibility(state);
        }
        panel.setVisibility(state);
    }
}