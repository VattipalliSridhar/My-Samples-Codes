package com.apps.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterNumbersAdapter extends RecyclerView.Adapter<ChapterNumbersAdapter.ViewDataClasses> {
    private int chaptersCount;
    Context context;
    private String  language;


    public ChapterNumbersAdapter(Context chapterListActivity, int chaptersCount, String lang_type) {
        this.context = chapterListActivity;
        this.chaptersCount = chaptersCount;
        language = lang_type;
    }

    @NonNull
    @Override
    public ViewDataClasses onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewDataClasses(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter_number_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewDataClasses holder, int position) {

        if(language.equals("telugu"))
        {
            holder.description.setText(String.valueOf(holder.getAdapterPosition() + 1)+" అధ్యాయం");
        }if(language.equals("english")){
            holder.description.setText(String.valueOf(holder.getAdapterPosition() + 1)+" Chapter");
        }if(language.equals("hindi")){
            holder.description.setText(String.valueOf(holder.getAdapterPosition() + 1)+" अध्याय");
        }


        if(position%2==0)
        {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
        }


    }

    @Override
    public int getItemCount() {
        return chaptersCount;
    }

    public class ViewDataClasses extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.layout)
        RelativeLayout layout;
        public ViewDataClasses(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
