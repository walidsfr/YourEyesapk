package com.example.youreyes.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.youreyes.MainActivity;
import com.example.youreyes.R;

import java.util.ArrayList;
import java.util.Locale;

public class ListenerActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
View V1 ;
View V2;
int N=0;
String text="test";
int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        V1=findViewById(R.id.v1);
V2=findViewById(R.id.v2);

V1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        speechtotext();
    }
});
V2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent d = new Intent(ListenerActivity.this,AnswerActivity.class);
    d.putExtra(AnswerActivity.MESSAGE,text);
    startActivity(d);
    finish();
    }
});


    }

    private void speechtotext(){
        Intent intent
                = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        }
        catch (Exception e) {
            Toast.makeText(ListenerActivity.this, "", Toast.LENGTH_SHORT).show();

        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    text=  result.get(0);
                    N=1;
                }
                break;
            }
        }
    }
}