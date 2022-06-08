package com.example.youreyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
Button bn;
TextView tx;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextToSpeech tss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx=findViewById(R.id.textView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        bn = findViewById(R.id.button);
        //
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},44);
        }




       bn.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
             getLocation();
               tts();
           }
       });
        

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {


            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                       tx.setText(addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }
    private void tts(){
                tss= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                         if(status==TextToSpeech.SUCCESS){
                             tss.setLanguage(Locale.ENGLISH);
                             tss.setSpeechRate(0.7f);
                             tss.speak(tx.getText().toString(),TextToSpeech.QUEUE_ADD,null);

                         }
                    }
                });



}
}