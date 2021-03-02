package com.apps.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apps.myapplication.nativeads.NativeTemplateStyle;
import com.apps.myapplication.nativeads.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class MainActivity2 extends AppCompatActivity {


    private static final String TAG = "--->Native Ad";



    private TemplateView template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


         template = findViewById(R.id.my_template);

        //---> initializing Google Ad SDK
     /*   MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(TAG, "Google SDK Initialized");


                AdLoader adLoader = new AdLoader.Builder(MainActivity2.this, "ca-app-pub-3940256099942544/2247696110")

                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                Log.d(TAG, "Native Ad Loaded");

                                if (isDestroyed()) {
                                    nativeAd.destroy();
                                    Log.d(TAG, "Native Ad Destroyed");
                                    return;
                                }


                                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                                template.setStyles(styles);
                                template.setVisibility(View.VISIBLE);
                                template.setNativeAd(nativeAd);

                            }
                        })

                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {
                                // Handle the failure by logging, altering the UI, and so on.
                                Log.d(TAG, "Native Ad Failed To Load");
                                template.setVisibility(View.GONE);

                            }
                        })
                        .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

                adLoader.loadAd(new AdRequest.Builder().build());
            }
        });*/


        MobileAds.initialize(this, initializationStatus -> {
            Log.d(TAG, "Google SDK Initialized");
            loadAd();
        });



    }



    private void loadAd() {
        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(false).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        AdLoader adLoader = new AdLoader.Builder(MainActivity2.this, "ca-app-pub-3940256099942544/1044960115")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        Log.d(TAG, "Native Ad Loaded");

                        if (isDestroyed()) {
                            nativeAd.destroy();
                            Log.d(TAG, "Native Ad Destroyed");
                            return;
                        }

                        if (nativeAd.getMediaContent().hasVideoContent()) {
                            float mediaAspectRatio = nativeAd.getMediaContent().getAspectRatio();
                            float duration = nativeAd.getMediaContent().getDuration();

                            nativeAd.getMediaContent().getVideoController().setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                                @Override
                                public void onVideoStart() {
                                    super.onVideoStart();
                                    Log.d(TAG, "Video Started");
                                }

                                @Override
                                public void onVideoPlay() {
                                    super.onVideoPlay();
                                    Log.d(TAG, "Video Played");
                                }

                                @Override
                                public void onVideoPause() {
                                    super.onVideoPause();
                                    Log.d(TAG, "Video Paused");
                                }

                                @Override
                                public void onVideoEnd() {
                                    super.onVideoEnd();
                                    Log.d(TAG, "Video Finished");
                                }

                                @Override
                                public void onVideoMute(boolean b) {
                                    super.onVideoMute(b);

                                    Log.d(TAG, "Video Mute : " + b);
                                }
                            });
                        }

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();


                        template.setStyles(styles);
                        template.setVisibility(View.VISIBLE);
                        template.setNativeAd(nativeAd);

                    }
                })

                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                        Log.d(TAG, "Native Ad Failed To Load");
                        template.setVisibility(View.GONE);

                        new CountDownTimer(10000, 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                Log.d(TAG, "Sec : " + millisUntilFinished / 1000);
                            }

                            @Override
                            public void onFinish() {
                                Log.d(TAG, "Reloading Native Ad");
                                loadAd();
                            }
                        }.start();

                    }
                })
                // .withNativeAdOptions(new NativeAdOptions.Builder().build())
                .withNativeAdOptions(adOptions)
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }
}