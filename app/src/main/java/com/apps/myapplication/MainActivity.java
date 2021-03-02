package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.myapplication.bible.BibleBook;
import com.apps.myapplication.bible.HindiBibleBook;
import com.apps.myapplication.bible.TeluguBibleBook;
import com.apps.myapplication.recyclerclick.ItemClickListener;
import com.apps.myapplication.recyclerclick.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ChapterAdapter chapterAdapter;
    private List<ChapterModel> list = new ArrayList();
    ChapterModel chapterModel;
    Context context;

    String lang_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        lang_type = getIntent().getStringExtra("language");


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chapters);
        JSONArray oldList = null;

        if (lang_type.equals("telugu")) {
            oldList = TeluguBibleBook.getInstance(getApplicationContext()).getOldTestanmentList();
        }
        if (lang_type.equals("hindi")) {
            oldList = HindiBibleBook.getInstance(getApplicationContext()).getOldTestanmentList();
        }
        if (lang_type.equals("english")) {
            oldList = BibleBook.getInstance(getApplicationContext()).getOldTestanmentList();
        }

        for (int i = 0; i < oldList.length(); i++) {
            chapterModel = new ChapterModel();
            try {
                Log.d("CHAP", oldList.getJSONObject(i).getString("key") + " " + oldList.getJSONObject(i).getString("val"));
                chapterModel.setKey(oldList.getJSONObject(i).getString("key"));
                chapterModel.setVal(oldList.getJSONObject(i).getString("val"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.list.add(chapterModel);
        }
        this.chapterAdapter = new ChapterAdapter(this.list, getApplicationContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(this.chapterAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(getApplicationContext(), ChapterListActivity.class)
                        .putExtra("chapter", list.get(position).getKey())
                        .putExtra("section", position)
                        .putExtra("language", lang_type));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}