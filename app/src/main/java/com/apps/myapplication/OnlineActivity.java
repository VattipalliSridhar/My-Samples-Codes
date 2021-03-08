package com.apps.myapplication;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.myapplication.adapter.RecyclerSongListAdapter;
import com.apps.myapplication.base.BaseActivity;
import com.apps.myapplication.model.SongModel;
import com.apps.myapplication.recyclerclick.ItemClickListener;
import com.apps.myapplication.recyclerclick.RecyclerTouchListener;
import com.apps.myapplication.viewmodel.SongViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class OnlineActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    ImageView imagePlayPause;
    TextView textCurrentTime, textTotalTime;
    SeekBar playerSeekBar;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();


    int pos;



    private SongViewModel songViewModel;
    private  ArrayList<SongModel.Song> songArrayList = new ArrayList<>();
    @BindView(R.id.songListRecyclerView)
    RecyclerView songListRecyclerView;

    private RecyclerSongListAdapter recyclerSongListAdapter;
    String audioUrl = "http://godsongs.weforapps.com/web_app/upload/Vishnusahasranama.wav05-03-2021-10:49:51";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        ButterKnife.bind(this);


        imagePlayPause = findViewById(R.id.imagePlayPause);
        textCurrentTime = findViewById(R.id.textCurrentTime);
        textTotalTime = findViewById(R.id.textTotalTime);
        playerSeekBar = findViewById(R.id.playerSeekBar);

        mediaPlayer = new MediaPlayer();

        playerSeekBar.setMax(100);


        songListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        songListRecyclerView.setHasFixedSize(true);
        recyclerSongListAdapter = new RecyclerSongListAdapter(this, songArrayList);
        songListRecyclerView.setAdapter(recyclerSongListAdapter);


        songViewModel = ViewModelProviders.of(this).get(SongViewModel.class);

        songViewModel.getListMutableLiveData().observe(this, new Observer<List<SongModel.Song>>() {
            @Override
            public void onChanged(List<SongModel.Song> songs) {
                songArrayList.clear();
                if (songs.size() > 0) {
                    songArrayList.addAll(songs);
                }
                recyclerSongListAdapter.notifyDataSetChanged();
                dismissDialog();
            }
        });
        songViewModel.getMessageToShow().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToastMessage(s);
                dismissDialog();
            }
        });


        songListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(OnlineActivity.this, songListRecyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("msg","re  : "+String.valueOf(songArrayList.get(position).getSongPath()));
                /*Intent intent = new Intent(OnlineActivity.this,OnlineActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);*/
                prepareMediaPlayer(position);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        if (isNetworkAvailable()) {
            getSong();
        }



       // pos = getIntent().getIntExtra("position", 0);
        Log.e("msg", "" + pos);



        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(runnable);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.ic_play);

                } else {
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.pause_btn);
                    updateSeekBar();
                }
            }
        });




        playerSeekBar.setOnTouchListener((v, event) -> {
            SeekBar seekBar = (SeekBar) v;
            int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
            mediaPlayer.seekTo(playPosition);
            textCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
            return false;
        });
        //mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> playerSeekBar.setSecondaryProgress(percent));

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            playerSeekBar.setProgress(0);
            imagePlayPause.setImageResource(R.drawable.ic_play);
            textCurrentTime.setText(R.string.zero);
            textTotalTime.setText(R.string.zero);
            mediaPlayer.reset();
            prepareMediaPlayer(pos);
            //dismissDialog();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepareMediaPlayer(int position) {
        //showDialog();
        try {
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());

            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();

            textTotalTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {


    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }


    private void getSong() {
        showDialog();
        songViewModel.getSongList(OnlineActivity.this);
    }
}

