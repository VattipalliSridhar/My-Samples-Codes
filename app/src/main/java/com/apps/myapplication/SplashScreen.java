package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.apps.myapplication.openads.AppOpenManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

      /*  Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
               startActivity(new Intent(SplashScreen.this,SampleAct.class));
            }
        }, 4000);*/
    }
}