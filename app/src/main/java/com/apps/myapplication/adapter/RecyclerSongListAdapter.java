package com.apps.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myapplication.R;
import com.apps.myapplication.SongListActivity;
import com.apps.myapplication.model.SongModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerSongListAdapter extends RecyclerView.Adapter<RecyclerSongListAdapter.ViewClasses> {
    Context mContext;

    private List<SongModel.Song> songList;

    public RecyclerSongListAdapter(Context songListActivity, ArrayList<SongModel.Song> songArrayList) {
        mContext=songListActivity;
        songList=songArrayList;
    }

    @NonNull
    @Override
    public ViewClasses onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.song_list_adapter_layout, parent, false);
        return new ViewClasses(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewClasses holder, int position) {

        holder.songTxt.setText(songList.get(position).getSongName());

        if (position % 2 == 1 || position == 0) {
            holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
        }
        if (position % 2 == 0) {
            holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        }

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewClasses extends RecyclerView.ViewHolder {


        @BindView(R.id.songTxt)
        TextView songTxt;

        @BindView(R.id.layout)
        RelativeLayout layout;

        public ViewClasses(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
