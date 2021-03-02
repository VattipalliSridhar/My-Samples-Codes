package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.apps.myapplication.bible.BibleBook;
import com.apps.myapplication.bible.HindiBibleBook;
import com.apps.myapplication.bible.TeluguBibleBook;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterActivity extends AppCompatActivity {

    private List<ChapterModel> list = new ArrayList();

    @BindView(R.id.chapters_Data_View)
    RecyclerView chapters_Data_View;
    @BindView(R.id.chapterName)
    TextView chapterName;

    String lang_type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        ButterKnife.bind(this);


        lang_type = getIntent().getStringExtra("language");
        chapterName.setText(getIntent().getExtras().getString("chapterName"));
        int chapterSectionPosition = getIntent().getIntExtra("section", 0);
        int chapterSection = getIntent().getIntExtra("chapter", 0);
        JSONArray verseForChapter = null;
        if (lang_type.equals("telugu")) {
            verseForChapter = TeluguBibleBook.getInstance(getApplicationContext()).getVerseForChapter(chapterSection, chapterSectionPosition);
        }
        if (lang_type.equals("english")) {
            verseForChapter = BibleBook.getInstance(getApplicationContext()).getVerseForChapter(chapterSection, chapterSectionPosition);
        }
        if (lang_type.equals("hindi")) {
            verseForChapter = HindiBibleBook.getInstance(getApplicationContext()).getVerseForChapter(chapterSection, chapterSectionPosition);
        }


        for (int i = 0; i < verseForChapter.length(); i++) {
            ChapterModel chapterModel = new ChapterModel();
            try {
                Log.e("CHAP", verseForChapter.getString(i) + " ");
                chapterModel.setVal((i + 1) + ". " + verseForChapter.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.list.add(chapterModel);


        }

        ChapterViewAdapter chapterViewAdapter = new ChapterViewAdapter(getApplicationContext(), list);
        chapters_Data_View.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chapters_Data_View.setAdapter(chapterViewAdapter);


    }


}