package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myapplication.base.BaseActivity;

import java.io.IOException;

public class MainActivity3 extends BaseActivity {

    private MediaPlayer mMediaPlayer;
    private ImageView mPlayerControl;
    TextView textCurrentTime, textTotalTime;
    SeekBar playerSeekBar;
    Handler handler = new Handler();
    String audioUrl = "http://godsongs.weforapps.com/web_app/upload/Venkateshashtakam.wav06-03-2021-17:20:06";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);




        mPlayerControl = (ImageView) findViewById(R.id.imagePlayPause);
        playerSeekBar = findViewById(R.id.playerSeekBar);

        textCurrentTime = findViewById(R.id.textCurrentTime);
        textTotalTime = findViewById(R.id.textTotalTime);
        mMediaPlayer = new MediaPlayer();
        playerSeekBar.setMax(100);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        prepareMediaPlayer();


        mPlayerControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });



        mMediaPlayer.setOnCompletionListener(mp -> {
            playerSeekBar.setProgress(0);
            mPlayerControl.setImageResource(R.drawable.ic_play);
            textCurrentTime.setText(R.string.zero);
            textTotalTime.setText(R.string.zero);
            mMediaPlayer.reset();
            prepareMediaPlayer();
        });

/*
        playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mMediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mMediaPlayer.seekTo(playPosition);
                return false;
            }
        });*/
    }

    private void prepareMediaPlayer() {


        showDialog();
        try {
            mMediaPlayer.setDataSource(audioUrl);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                textTotalTime.setText(milliSecondsToTimer(mMediaPlayer.getDuration()));
                dismissDialog();
                togglePlayPause();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayerControl.setImageResource(R.drawable.ic_play);
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekBar.setSecondaryProgress(percent);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            updateSeekBar();
            long currentDuration = mMediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            handler.removeCallbacks(runnable);
            mPlayerControl.setImageResource(R.drawable.ic_play);
        } else {
            mMediaPlayer.start();
            mPlayerControl.setImageResource(R.drawable.ic_pause);
            updateSeekBar();
        }
    }

    private void updateSeekBar() {
        if (mMediaPlayer.isPlaying()) {
            playerSeekBar.setProgress((int) (((float) mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration()) * 100));
            handler.postDelayed(runnable, 1000);
        }
    }


    private String milliSecondsToTimer(long milliSeconds) {
        String timerString = "";
        String secondString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return timerString;


    }

}