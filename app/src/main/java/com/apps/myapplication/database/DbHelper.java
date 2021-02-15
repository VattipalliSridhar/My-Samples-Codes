package com.apps.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.myapplication.model.EventsModel;
import com.apps.myapplication.model.FestivalModle;
import com.apps.myapplication.model.PanchangamModel;

import java.util.ArrayList;

public class DbHelper {
    public static String TAG = "DbHelper";

  /*  public static ArrayList<MuhurthamModle> getMuhurthams(Context context) {
        ArrayList<MuhurthamModle> arrayList = new ArrayList<>();
        arrayList.clear();
        Cursor rawQuery = new AssetDatabaseOpenHelper(context).openDatabase().rawQuery("select * from MuhurthamTable;", (String[]) null);
        if (((long) rawQuery.getCount()) > 0) {
            while (rawQuery.moveToNext()) {
                MuhurthamModle muhurthamModle = new MuhurthamModle();
                muhurthamModle.setmType(Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("MType"))));
                muhurthamModle.setmStar(rawQuery.getString(rawQuery.getColumnIndex("MStar")));
                muhurthamModle.setmDetails(rawQuery.getString(rawQuery.getColumnIndex("MDetails")));
                muhurthamModle.setmDetailsOne(rawQuery.getString(rawQuery.getColumnIndex("MDetailsOne")));
                muhurthamModle.setmMonth(rawQuery.getString(rawQuery.getColumnIndex("MMonth")));
                muhurthamModle.setmYear("" + rawQuery.getInt(rawQuery.getColumnIndex("MYear")));
                arrayList.add(muhurthamModle);
            }
        }
        return arrayList;
    }

    public static ArrayList<EventsModel> getCalenderEvents(Context context) {
        ArrayList<EventsModel> arrayList = new ArrayList<>();
        arrayList.clear();
        Cursor rawQuery = new AssetDatabaseOpenHelper(context).openDatabase().rawQuery("select * from festivals;", (String[]) null);
        if (((long) rawQuery.getCount()) > 0) {
            while (rawQuery.moveToNext()) {
                EventsModel eventsModel = new EventsModel();
                eventsModel.setDate(rawQuery.getString(rawQuery.getColumnIndex("Date")));
                eventsModel.setName(rawQuery.getString(rawQuery.getColumnIndex("Name")));
                arrayList.add(eventsModel);
            }
        }
        return arrayList;
    }
*/

    public static ArrayList<EventsModel> getCalenderEvents(Context context) {
        ArrayList<EventsModel> arrayList = new ArrayList<>();
        arrayList.clear();
        Cursor rawQuery = new AssetDatabaseOpenHelper(context).openDatabase().rawQuery("select * from festivals;", (String[]) null);
        if (((long) rawQuery.getCount()) > 0) {
            while (rawQuery.moveToNext()) {
                EventsModel eventsModel = new EventsModel();
                eventsModel.setDate(rawQuery.getString(rawQuery.getColumnIndex("Date")));
                eventsModel.setName(rawQuery.getString(rawQuery.getColumnIndex("Name")));
                arrayList.add(eventsModel);
            }
        }
        return arrayList;
    }
    public static ArrayList<FestivalModle> getFestivalTableDetails(Context context) {
        ArrayList<FestivalModle> arrayList = new ArrayList<>();
        arrayList.clear();
        Cursor rawQuery = new AssetDatabaseOpenHelper(context).openDatabase().rawQuery("select * from festivals;", (String[]) null);
        if (((long) rawQuery.getCount()) > 0) {
            while (rawQuery.moveToNext()) {
                FestivalModle festivalModle = new FestivalModle();
                festivalModle.setFestivalDate(rawQuery.getString(rawQuery.getColumnIndex("Date")));
                festivalModle.setFestivalName(rawQuery.getString(rawQuery.getColumnIndex("Name")));
                arrayList.add(festivalModle);
            }
        }
        return arrayList;
    }

    public static ArrayList<PanchangamModel> getPanchangamTableDetails(Context context) {
        ArrayList<PanchangamModel> arrayList = new ArrayList<>();
        arrayList.clear();
        Cursor rawQuery = new AssetDatabaseOpenHelper(context).openDatabase().rawQuery("select * from panchangam;", (String[]) null);
        long count = (long) rawQuery.getCount();
        String str = TAG;
        Log.e(str, "Records Count=====" + count);
        if (count > 0) {
            while (rawQuery.moveToNext()) {
                try {
                    PanchangamModel panchangamModel = new PanchangamModel();
                    panchangamModel.setpDate(rawQuery.getString(rawQuery.getColumnIndex("Date")));
                    panchangamModel.setpFTwo(rawQuery.getString(rawQuery.getColumnIndex("Festival")));
                    panchangamModel.setpMasam(rawQuery.getString(rawQuery.getColumnIndex("MonthName")));
                    panchangamModel.setpPaksham(rawQuery.getString(rawQuery.getColumnIndex("Paksha")));
                    panchangamModel.setpSuryodam(rawQuery.getString(rawQuery.getColumnIndex("Sunrise")));
                    panchangamModel.setpSuryasthamayam(rawQuery.getString(rawQuery.getColumnIndex("Sunset")));
                    panchangamModel.setpAyana(rawQuery.getString(rawQuery.getColumnIndex("Ayana")));
                    panchangamModel.setpThidiOne(rawQuery.getString(rawQuery.getColumnIndex("Tithi")) + "," + rawQuery.getString(rawQuery.getColumnIndex("VedicRitu")) + "," + rawQuery.getString(rawQuery.getColumnIndex("MonthName")));
                    panchangamModel.setpNakshtramOne(rawQuery.getString(rawQuery.getColumnIndex("Nakshatra")));
                    panchangamModel.setpKaranaOne(rawQuery.getString(rawQuery.getColumnIndex("Karana")));
                    panchangamModel.setpYougaOne(rawQuery.getString(rawQuery.getColumnIndex("Yoga")));
                    panchangamModel.setpSubhavarkthaOne(rawQuery.getString(rawQuery.getColumnIndex("AmritKalam")));
                    panchangamModel.setpSubhavarkthaTwo(rawQuery.getString(rawQuery.getColumnIndex("GulikaiKalam")));
                    panchangamModel.setpSubhavarkthaThree(rawQuery.getString(rawQuery.getColumnIndex("AbhijitMuhurtam")));
                    panchangamModel.setpBuravarakthaOne(rawQuery.getString(rawQuery.getColumnIndex("Durmuhurtam")));
                    panchangamModel.setpBuravarakthaTwo(rawQuery.getString(rawQuery.getColumnIndex("Varjyam")));
                    panchangamModel.setpBuravarakthaThree(rawQuery.getString(rawQuery.getColumnIndex("RahuKalam")));
                    panchangamModel.setpBuravarakthaFour(rawQuery.getString(rawQuery.getColumnIndex("Yamagandam")));
                    panchangamModel.setpTeluguYear(rawQuery.getString(rawQuery.getColumnIndex("YearName")));
                    arrayList.add(panchangamModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }
}
