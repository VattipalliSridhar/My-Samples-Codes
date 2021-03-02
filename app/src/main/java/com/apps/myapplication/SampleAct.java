package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class SampleAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        initAd();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Write whatever to want to do after delay specified (1 sec)
                //startActivity(new Intent(SampleAct.this,SplashScreen.class));
            }
        }, 4000);
    }

    private void initAd() {
        initAppOpenAds();
    }

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private void initAppOpenAds() {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(AppOpenAd ad) {
                ad.show(SampleAct.this, new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();

                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        startActivity(new Intent(SampleAct.this,SplashScreen.class));
                    }
                });

            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {

            }
        };

        AppOpenAd.load(this, getString(R.string.app_open_ads1), new AdRequest.Builder().build(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback);

    }
}