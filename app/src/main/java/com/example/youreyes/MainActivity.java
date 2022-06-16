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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


TextView tx;
String text="xy";
View v;
int N =0;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextToSpeech tss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
v=findViewById(R.id.view2);
tx= findViewById(R.id.tx);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        //

texttospeech("Hi I am your eyes what I can help you");
        Thread T;
      v.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
N=0;
text="";
                              speechtotext();
               new Thread(new Runnable() {
                                  @Override
                                  public void run() {
                                      while (N == 0) {

                                      }
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              N=0;
                                              switch (text){
                                                  case "my location":
                                                      getLocation();
                                                      N=0;
                                                      text="";
                                                      break;
                                                  case "time":
                                                      getdate();
                                                      N=0;
                                                      text="";
                                                      break;
                                                  default:
                                                      texttospeech("I don't understand");
                                                      N=0;
                                                      text="";
                                                      break;

                                              }
                                              tx.setText("test 1");

                                          }
                                      });
                                      tx.setText("test 2");
                                  }



                              }).start();
              tx.setText("test 3");
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
        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

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