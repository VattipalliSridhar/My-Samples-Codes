package com.apps.myapplication;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BibleBook {
    private static BibleBook book;
    JSONArray bookList = new JSONArray();
    JSONArray chaptersList = new JSONArray();
    ArrayList<String> oldTestanment = new ArrayList<>();
    JSONArray oldTestanmentList = new JSONArray();

    public static BibleBook getInstance(Context context) {
        BibleBook bibleBook = book;
        return bibleBook == null ? new BibleBook(context) : bibleBook;
    }

    public BibleBook(Context context) {
        loadOldTestanment(context);
        loadJSONFromAsset(context);
    }

    public void loadJSONFromAsset(Context context) {
        String str;
        try {
            InputStream open = context.getAssets().open("bible.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            str = new String(bArr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            str = null;
        }
        try {
            this.chaptersList = new JSONArray(str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public JSONArray getVerseForChapter(int i, int i2) {
        try {
            return this.chaptersList.getJSONObject(i).getJSONArray("chapters").getJSONArray(i2);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getChaptersCount(int i) {
        try {
            return this.chaptersList.getJSONObject(i).getJSONArray("chapters").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void loadOldTestanment(Context context) {
        String str;
        try {
            InputStream open = context.getAssets().open("oldchapters.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            str = new String(bArr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            str = null;
        }
        try {
            this.oldTestanmentList = new JSONObject(str).getJSONArray("old");
            Log.d("CHAP", this.oldTestanmentList + " ");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public JSONArray getChaptersList() {
        return this.chaptersList;
    }

    public void setChaptersList(JSONArray jSONArray) {
        this.chaptersList = jSONArray;
    }

    public JSONArray getBookList() {
        return this.bookList;
    }

    public void setBookList(JSONArray jSONArray) {
        this.bookList = jSONArray;
    }

    public JSONArray getOldTestanmentList() {
        return this.oldTestanmentList;
    }

    public void setOldTestanmentList(JSONArray jSONArray) {
        this.oldTestanmentList = jSONArray;
    }

    public ArrayList<String> getOldTestanment() {
        return this.oldTestanment;
    }

    public void setOldTestanment(ArrayList<String> arrayList) {
        this.oldTestanment = arrayList;
    }
}
