package com.apps.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterViewAdapter extends RecyclerView.Adapter<ChapterViewAdapter.ChapterDataView> {
    private Context context;
    private List<ChapterModel> chapterModelList;

    public ChapterViewAdapter(Context applicationContext, List<ChapterModel> list) {
        context=applicationContext;
        chapterModelList=list;

    }

    @NonNull
    @Override
    public ChapterDataView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChapterDataView(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_verse_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterDataView holder, int position) {
        holder.description.setText(""+chapterModelList.get(position).getVal());

    }

    @Override
    public int getItemCount() {
        return chapterModelList.size();
    }

    public class ChapterDataView extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView description;
        public ChapterDataView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
