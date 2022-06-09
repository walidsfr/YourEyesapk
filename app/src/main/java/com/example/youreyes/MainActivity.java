package com.example.youreyes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    Button bn;
TextView tx;
String text="xy";
    int N=0;
   boolean a =false;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
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




    speechtotext();
        texttospeech(text);









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
                       tx.setText(addresses.get(0).getCountryName()+addresses.get(0).getLocality());
                        texttospeech(addresses.get(0).getCountryName()+addresses.get(0).getLocality());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

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
    private boolean speechtotext(){
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
        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

    }
return true ;
}
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
text =Objects.requireNonNull(result).get(0) ;
N=1;

            }
        }}
    private void tiem(){
        try {
            while (a==true){
                tx.setText(text);
            }
        }catch (Exception e){

        }
    }
private void waiting (){

    while (N==0){
            try {
                this.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

}
}