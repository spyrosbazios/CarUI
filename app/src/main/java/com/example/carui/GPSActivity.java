package com.example.carui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, UpdateUtilities{

    private ImageButton homeButton, moreButton, searchAddressButton, voiceButton, callerBubble;
    private LinearLayout favouritesPanel, newFavouritePanel;
    private boolean moreState = false;
    private Button[] favourites = new Button[5];
    private EditText searchAddressEditText, newLocationEditText, newAddressEditText;
    private Button newCancelButton, newAddButton;
    private int newFavouritePos;
    private SpeechRecognizer speechRecognizer;
    final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    private final int RECORD_AUDIO_REQUEST_CODE = 1;

    @SuppressLint("ClickableViewAccessibility")
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

        callerBubble = (ImageButton)findViewById(R.id.callerbubble_btn);
        callerBubble.setOnClickListener(this);
        callerBubble.setOnTouchListener(this);
        if (Utilities.CALLER == null) callerBubble.setVisibility(View.GONE);
        if (Utilities.BUBBLE_POSITION != null) callerBubble.setLayoutParams(Utilities.BUBBLE_POSITION);

        voiceButton = (ImageButton)findViewById(R.id.voice_btn);
        voiceButton.setOnClickListener(this);
        askVoicePermission();
        handleSpeechRecognizer();
    }

    @Override
    protected void onPause() {super.onPause();}

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }*/

    @Override
    public void onBackPressed() {
        speechRecognizer.destroy();
        this.updateUtilities();
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
        else if (v == voiceButton) {
            if ((Utilities.CALLER != null) && (Utilities.CALL_STATE != CallingActivity.CALLSTATE.HOLD))
                Toast.makeText(GPSActivity.this, "Please put call on hold to use voice recognition", Toast.LENGTH_SHORT).show();
            else {
                Utilities.PLAY_STATE = false;
                Toast.makeText(GPSActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                speechRecognizer.cancel();
                speechRecognizer.startListening(speechRecognizerIntent);
            }
        }
        else if (v == newCancelButton || v == newAddButton) {
            setPanelVisibility(newFavouritePanel, false);
            if (v == newAddButton && newLocationEditText.getText().length() > 0 && newAddressEditText.getText().length() > 0) {
                favourites[newFavouritePos].setText(String.valueOf(newLocationEditText.getText() + ": " + newAddressEditText.getText()));
            }
        }
        else if (v == callerBubble) {
            startActivity(new Intent(GPSActivity.this, CallingActivity.class));
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)callerBubble.getLayoutParams();
        int startX = 0;
        int startY = 0;
        int newX = 0;
        int newY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = layoutParams.leftMargin;
                startY = layoutParams.topMargin;
                break;
            case MotionEvent.ACTION_MOVE:
                newX = Math.min((int)event.getRawX(), displayMetrics.widthPixels);
                newY = Math.min((int)event.getRawY(), displayMetrics.heightPixels);
                layoutParams.leftMargin = newX - callerBubble.getLayoutParams().width;
                layoutParams.topMargin = newY;// - callerBubble.getLayoutParams().height;
                callerBubble.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(newX - startX) < 1 && Math.abs(newY - startY) < 1) {
                    this.updateUtilities();
                    startActivity(new Intent(GPSActivity.this, CallingActivity.class));
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void updateUtilities() {
        for (int i = 0; i < favourites.length; i++)
            Utilities.GPS_FAVOURITES[i] = String.valueOf(favourites[i].getText());
        Utilities.BUBBLE_POSITION = (ConstraintLayout.LayoutParams)callerBubble.getLayoutParams();
    }

    private void setPanelVisibility(LinearLayout panel, boolean flag) {
        int state = flag ? View.VISIBLE : View.GONE;
        for (int i = 0; i < panel.getChildCount(); i++) {
            panel.getChildAt(i).setVisibility(state);
        }
        panel.setVisibility(state);
    }

    private void handleSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el_GR");
        //speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() {speechRecognizer.stopListening();}
            @Override public void onError(int error) {}
            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
            @Override public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                searchAddressEditText.setText(data.get(0));
            }
        });;
    }

    private void askVoicePermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
    }
}