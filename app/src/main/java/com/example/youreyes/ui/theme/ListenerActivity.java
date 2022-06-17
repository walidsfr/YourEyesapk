package com.example.youreyes.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.youreyes.MainActivity;
import com.example.youreyes.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class ListenerActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
View V1 ;
View V2;

String text="xxxx";
TextToSpeech tss ;

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
        if(text.equals("xxxx")){
            texttospeech("Please click the top to tell me your order");
        }else {
            Intent d = new Intent(ListenerActivity.this,AnswerActivity.class);
            d.putExtra(AnswerActivity.MESSAGE,text);
            startActivity(d);
            finish();
        }

    }
});


    }

    private void speechtotext(){
        Intent intent
                = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.ENGLISH);
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
                    switch (result.get(0)){
                        case "my location":
                            texttospeech("Click below to confirm your order");

                            break;
                        case "date":
                            texttospeech("Click below to confirm your order");
                            break;
                        case "what time is it":
                            texttospeech("Click below to confirm your order");
                            break;
                        case "open camera":
                            getcamera();
                            text="xxxx";
                            break;

                        default:
                            texttospeech("I don't understand , please repeat");
                            text="xxxx";
                            break;

                    }




                }
                break;
            }

        }
    }
    private void getcamera(){
        Intent open_Camera =new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
        startActivityForResult(open_Camera,100);
    }

    private void texttospeech(String a){
        tss= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    tss.setLanguage(Locale.ENGLISH);
                    tss.setSpeechRate(0.7f);
                    tss.speak(a,TextToSpeech.QUEUE_ADD,null);

                }
            }
        });



    }
}