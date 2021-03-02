package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apps.myapplication.bible.BibleBook;
import com.apps.myapplication.bible.HindiBibleBook;
import com.apps.myapplication.bible.TeluguBibleBook;
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

    String lang_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        ButterKnife.bind(this);


        chapterName.setText(getIntent().getExtras().getString("chapter"));
        lang_type = getIntent().getStringExtra("language");
        int intExtra = getIntent().getIntExtra("section", 0);
        int chaptersCount = 0;

        if (lang_type.equals("telugu")) {
            TeluguBibleBook teluguBibleBook = TeluguBibleBook.getInstance(this);
            chaptersCount = teluguBibleBook.getChaptersCount(intExtra);
        }
        if (lang_type.equals("hindi")) {
            HindiBibleBook hindiBibleBook = HindiBibleBook.getInstance(this);
            chaptersCount = hindiBibleBook.getChaptersCount(intExtra);
        }
        if (lang_type.equals("english")) {
            BibleBook instance = BibleBook.getInstance(this);
            chaptersCount = instance.getChaptersCount(intExtra);
        }





        Log.e("msg", "" + intExtra + "  " + chaptersCount + "  " + getIntent().getExtras().getString("chapter"));
        chapterNumbersAdapter = new ChapterNumbersAdapter(this, chaptersCount,lang_type);
        chapters_list_of_number.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chapters_list_of_number.setAdapter(this.chapterNumbersAdapter);
        chapters_list_of_number.addOnItemTouchListener(new RecyclerTouchListener(ChapterListActivity.this, chapters_list_of_number, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(getApplicationContext(), ChapterActivity.class)
                        .putExtra("chapter", intExtra)
                        .putExtra("chapterName", chapterName.getText().toString().trim())
                        .putExtra("section", position)
                        .putExtra("language", lang_type));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}