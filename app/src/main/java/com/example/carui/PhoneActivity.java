package com.example.carui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener, UpdateUtilities{

    private ImageView musicLeftButton, musicRightButton;
    private ImageButton homeButton, playButton, voiceButton;
    private TextView artistTextView, titleTextView;
    private ListView contactListView;
    private int s;
    private final String[] songList = {"SAINt JHN-Roses", "Kanye West-Gorgeous", "Frank Ocean-Pyramids"};
    private final String[] contactList = {"Spyros", "Christos", "Manos", "Nantia", "Ion Androutsopoulos", "Chalkidis", "Dio", "Chara", "George"};
    private SeekBar musicSeekBar;
    private boolean playState;
    private SpeechRecognizer speechRecognizer;
    final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    private final int RECORD_AUDIO_REQUEST_CODE = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        homeButton = (ImageButton)findViewById(R.id.home_btn);
        homeButton.setOnClickListener(this);

        s = Utilities.SONG;
        artistTextView = (TextView)(findViewById(R.id.artist_textview));
        titleTextView = (TextView)(findViewById(R.id.songtitle_textview));
        String[] st = songList[s].split("-", 2);
        artistTextView.setText(st[0]);
        titleTextView.setText(st[1]);

        musicLeftButton = (ImageView)findViewById(R.id.musicprevious_img);
        musicLeftButton.setOnClickListener(this);
        musicRightButton = (ImageView)findViewById(R.id.musicnext_img);
        musicRightButton.setOnClickListener(this);
        playButton = (ImageButton)findViewById(R.id.musicplay_btn);
        playButton.setOnClickListener(this);
        playState = Utilities.PLAY_STATE;
        if (playState)
            playButton.setBackgroundResource(R.drawable.musicpause);
        else
            playButton.setBackgroundResource(R.drawable.musicplay);

        musicSeekBar = (SeekBar)findViewById(R.id.musicbar_seekbar);
        musicSeekBar.setOnClickListener(this);
        musicSeekBar.setMax(100);
        musicSeekBar.setProgress(Utilities.MUSIC_PROGRESS);

        contactListView = (ListView)findViewById(R.id.contacts_listview);
        final ArrayAdapter<String> contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView)view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        contactListView.setAdapter(contactAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callContact(contactAdapter.getItem(position));
            }
        });

        voiceButton = (ImageButton)findViewById(R.id.voice_btn);
        voiceButton.setOnClickListener(this);
        askVoicePermission();
        handleSpeechRecognizer();
    }

    @Override
    protected void onPause() {super.onPause();}

    @Override
    public void onBackPressed() {
        this.updateUtilities();
        startActivity(new Intent(PhoneActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == homeButton)
            onBackPressed();
        else if (v == playButton) {
            playState = !playState;
            if (playState)
                playButton.setBackgroundResource(R.drawable.musicpause);
            else
                playButton.setBackgroundResource(R.drawable.musicplay);

        }
        else if (v == musicLeftButton || v == musicRightButton) {
            if (v == musicLeftButton) s--;
            else s++;
            s = Utilities.clampIntToLimits(s, 0, songList.length-1);
            String[] st = songList[s].split("-", 2);
            artistTextView.setText(st[0]);
            titleTextView.setText(st[1]);
        }
        else if (v == voiceButton) {
            Utilities.PLAY_STATE = false;
            Toast.makeText(PhoneActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
            speechRecognizer.cancel();
            speechRecognizer.startListening(speechRecognizerIntent);
        }
    }

    @Override
    public void updateUtilities() {
        Utilities.SONG = s;
        Utilities.PLAY_STATE = playState;
        Utilities.MUSIC_PROGRESS = musicSeekBar.getProgress();
    }

    private void callContact(String callTarget) {
        Utilities.CALLER = callTarget.trim();
        Utilities.CALL_STATE = CallingActivity.CALLSTATE.RUNNING;
        updateUtilities();
        Utilities.startTimer();
        startActivity(new Intent(PhoneActivity.this, CallingActivity.class));
    }

    private void handleSpeechRecognizer() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        String lang = null;
        if (Utilities.LANGUAGE == 0) lang = "en_001";
        else if (Utilities.LANGUAGE == 1) lang = "el_GR";
        else if (Utilities.LANGUAGE == 2) lang = "es_";
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
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
                if (!data.get(0).contains(" ")) return;
                //Toast.makeText(PhoneActivity.this, data.get(0), Toast.LENGTH_SHORT).show();
                String[] r = data.get(0).split(" ", 2);
                if (r[0].equalsIgnoreCase("call")) {
                    for (String c : contactList) {
                        if (c.equalsIgnoreCase(r[1]))
                            callContact(c);
                    }
                }
                else Toast.makeText(PhoneActivity.this, "Speech recognition failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askVoicePermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
    }
}