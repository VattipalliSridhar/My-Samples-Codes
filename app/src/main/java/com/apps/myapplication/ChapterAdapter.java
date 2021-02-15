package com.apps.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterVideHolder> {

    Context context;

    List<ChapterModel> playerDataModelList;
    public ChapterAdapter(List<ChapterModel> list, Context applicationContext, MainActivity mainActivity) {
        this.context = applicationContext;
        this.playerDataModelList = list;
    }

    @NonNull
    @Override
    public ChapterVideHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChapterVideHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ChapterVideHolder holder, int position) {
        ChapterModel chapterModel = this.playerDataModelList.get(position);
        holder.titleTV.setText(chapterModel.getKey());
        holder.descriptionTV.setText(chapterModel.getVal());
       // holder.setClicklistener(this.feedClickListener, chapterModel);
    }

    @Override
    public int getItemCount() {
        return playerDataModelList.size();
    }

    public static class ChapterVideHolder extends RecyclerView.ViewHolder {
        /* access modifiers changed from: private */
        public TextView descriptionTV;
        /* access modifiers changed from: private */
        public TextView titleTV;

        public ChapterVideHolder(View view) {
            super(view);
            this.titleTV = (TextView) view.findViewById(R.id.title);
            this.descriptionTV = (TextView) view.findViewById(R.id.description);
        }


    }
}
