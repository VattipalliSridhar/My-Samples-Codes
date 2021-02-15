package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class SongListActivity extends BaseActivity {

    private SongViewModel songViewModel;
    public static ArrayList<SongModel.Song> songArrayList = new ArrayList<>();
    @BindView(R.id.songListRecyclerView)
    RecyclerView songListRecyclerView;

    private RecyclerSongListAdapter recyclerSongListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        ButterKnife.bind(this);

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


        songListRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(SongListActivity.this, songListRecyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("msg","re  : "+String.valueOf(songArrayList.get(position).getSongPath()));
                Intent intent = new Intent(SongListActivity.this,OnlineActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        if (isNetworkAvailable()) {
            getSong();
        }


    }

    private void getSong() {
        showDialog();
        songViewModel.getSongList(SongListActivity.this);
    }
}