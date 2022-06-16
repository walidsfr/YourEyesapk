package com.example.youreyes.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.PopUpToBuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.example.youreyes.MainActivity;
import com.example.youreyes.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnswerActivity extends AppCompatActivity {
    TextView tx;
    TextToSpeech tss;
    public static final String MESSAGE = "message";
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        tx=findViewById(R.id.Atx);
        Intent i= getIntent();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AnswerActivity.this);
String message = i.getStringExtra(MESSAGE);

           getLocation();




    }
    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {


            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(AnswerActivity.this, Locale.ENGLISH);
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        texttospeech(addresses.get(0).getCountryName()+addresses.get(0).getLocality());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }
    private void getdate(){
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        texttospeech(formattedDate);
        tx.setText(formattedDate);
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