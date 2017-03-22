package com.example.a510.smart2;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    protected Button btSpeech;
    protected TextView txSpeech;
    private static final int SPEECH_CODE = 1234;
    protected TextToSpeech tts;

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(0.5f);
            tts.setSpeechRate(1.0f);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SPEECH_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String strSpeech = arrayList.get(0);
                txSpeech.setText(strSpeech);
                String text = txSpeech.getText().toString();
                tts.speak(strSpeech, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txSpeech = (TextView) findViewById(R.id.txSpeech);
        btSpeech = (Button)findViewById(R.id.btSpeech);

        btSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Recognizeing....");
                startActivityForResult(intent, SPEECH_CODE);
            }
        });

        tts = new TextToSpeech(this, this);
    }
}