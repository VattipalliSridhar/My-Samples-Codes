package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.myapplication.recyclerclick.ItemClickListener;
import com.apps.myapplication.recyclerclick.RecyclerTouchListener;
import com.apps.myapplication.utils.Pref;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ChapterAdapter chapterAdapter;

    private List<ChapterModel> list = new ArrayList();
    ChapterModel chapterModel;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chapters);
        JSONArray oldList = BibleBook.getInstance(getApplicationContext()).getOldTestanmentList();
        for (int i = 0; i < oldList.length(); i++) {
             chapterModel = new ChapterModel();
            try {
                Log.d("CHAP", oldList.getJSONObject(i).getString("key") + " "+oldList.getJSONObject(i).getString("val"));
                chapterModel.setKey(oldList.getJSONObject(i).getString("key"));
                chapterModel.setVal(oldList.getJSONObject(i).getString("val"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.list.add(chapterModel);
        }
        this.chapterAdapter = new ChapterAdapter(this.list,getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.chapterAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(getApplicationContext(), ChapterListActivity.class)
                        .putExtra("chapter", list.get(position).getKey())
                        .putExtra("section", position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}