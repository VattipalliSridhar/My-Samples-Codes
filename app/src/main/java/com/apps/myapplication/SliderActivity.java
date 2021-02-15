package com.apps.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.apps.myapplication.adapter.SliderAdapter;
import com.apps.myapplication.model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        viewPager2 = findViewById(R.id.viewPagerSlider);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.app_icon));
        sliderItems.add(new SliderItem(R.drawable.app_icon));
        sliderItems.add(new SliderItem(R.drawable.app_icon));
        sliderItems.add(new SliderItem(R.drawable.app_icon));
        sliderItems.add(new SliderItem(R.drawable.app_icon));
        sliderItems.add(new SliderItem(R.drawable.app_icon));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        //viewPager2.addOnPageChangeListe

    }
}