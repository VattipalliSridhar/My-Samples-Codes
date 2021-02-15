package com.apps.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myapplication.model.ModelHome;
import com.apps.myapplication.utils.Pref;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {
    Context context;
    ArrayList<ModelHome> arrayListData;

    public AdapterHome(HomeActivity homeActivity, ArrayList<ModelHome> arrayList) {
        context=homeActivity;
        arrayListData=arrayList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewDays.setText(this.arrayListData.get(position).getDayValue());
        holder.imageViewGod.setImageResource(Integer.parseInt(this.arrayListData.get(position).getImageView()));
        if (Pref.getString("day", this.context).equals(this.arrayListData.get(position).getDayName())) {
            holder.imageViewGod.setBackgroundResource(R.color.purple_200);
            setAnimation(holder.cardView);
            return;
        }
        holder.imageViewGod.setBackgroundResource(R.color.colorAccent);
    }

    private void setAnimation(View cardView) {
        cardView.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.scaleday));
    }

    @Override
    public int getItemCount() {
        return arrayListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout cardView;
        ImageView imageViewGod;
        TextView textViewDays;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardView = itemView.findViewById(R.id.card_view);
            this.textViewDays = itemView.findViewById(R.id.textViewDayName);
            this.imageViewGod = itemView.findViewById(R.id.imageViewHomeGod);
        }
    }
}
