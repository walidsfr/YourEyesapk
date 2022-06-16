package com.example.youreyes.ui.theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.example.youreyes.MainActivity;
import com.example.youreyes.R;

import java.util.Locale;

public class FirstActivity extends AppCompatActivity {

    TextToSpeech tss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if(ActivityCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FirstActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},44);
        }
        texttospeech("Hi , what I can help you");


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