package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apps.myapplication.recyclerclick.ItemClickListener;
import com.apps.myapplication.recyclerclick.RecyclerTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterListActivity extends AppCompatActivity {

    @BindView(R.id.chapterName)
    TextView chapterName;

    @BindView(R.id.chapters_list_of_number)
    RecyclerView chapters_list_of_number;


    public ChapterNumbersAdapter chapterNumbersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        ButterKnife.bind(this);


        chapterName.setText(getIntent().getExtras().getString("chapter"));
        int intExtra = getIntent().getIntExtra("section", 0);
        BibleBook instance = BibleBook.getInstance(this);
        int chaptersCount = instance.getChaptersCount(intExtra);
        Log.e("msg", "" + intExtra + "  " + chaptersCount + "  " + getIntent().getExtras().getString("chapter"));
        chapterNumbersAdapter = new ChapterNumbersAdapter(this, chaptersCount);
        chapters_list_of_number.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chapters_list_of_number.setAdapter(this.chapterNumbersAdapter);
        chapters_list_of_number.addOnItemTouchListener(new RecyclerTouchListener(ChapterListActivity.this, chapters_list_of_number, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(getApplicationContext(), ChapterActivity.class)
                        .putExtra("chapter", intExtra)
                        .putExtra("chapterName", chapterName.getText().toString().trim())
                        .putExtra("section", position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}