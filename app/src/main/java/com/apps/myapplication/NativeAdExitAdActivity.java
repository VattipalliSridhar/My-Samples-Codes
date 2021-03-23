package com.apps.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apps.myapplication.nativeads.NativeTemplateStyle;
import com.apps.myapplication.nativeads.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class NativeAdExitAdActivity extends AppCompatActivity {


    NativeAd mNativeAd;


    private static final String TAG = "--->Native Ad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad_exit_ad);

        loadNativeAd();
    }


    private void loadNativeAd() {
        AdLoader adLoader = new AdLoader.Builder(NativeAdExitAdActivity.this, "ca-app-pub-3940256099942544/2247696110")

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

                        mNativeAd = nativeAd;

                    }
                })

                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                        Log.d(TAG, "Native Ad Failed To Load");

                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder exitBuilderDialog = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.exit_layout, null);

        Button yesBtm = dialogView.findViewById(R.id.id_exit_pos_btm);
        Button noBtm = dialogView.findViewById(R.id.id_exit_neg_btm);
        TextView txtTitle = dialogView.findViewById(R.id.id_exit_title);
        TextView txtMessage = dialogView.findViewById(R.id.id_exit_message);
        TemplateView template = dialogView.findViewById(R.id.my_template);

        template.setVisibility(View.GONE);

        if (mNativeAd != null) {
            NativeTemplateStyle styles = new
                    NativeTemplateStyle.Builder().build();


            template.setStyles(styles);
            template.setVisibility(View.VISIBLE);
            template.setNativeAd(mNativeAd);
        }


        txtTitle.setText("Exit");
        txtMessage.setText("Do you want to exit?");

        exitBuilderDialog.setView(dialogView);
        final AlertDialog alertDialog = exitBuilderDialog.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        yesBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentExit = new Intent(Intent.ACTION_MAIN);
                intentExit.addCategory(Intent.CATEGORY_HOME);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentExit);

                finish();
                System.exit(0);
            }
        });

        noBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNativeAd != null) {
                    mNativeAd.destroy();
                }
                loadNativeAd();
                alertDialog.cancel();
            }
        });

    }
}