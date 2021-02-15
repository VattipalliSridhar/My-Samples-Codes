package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.apps.myapplication.model.ModelHome;
import com.apps.myapplication.utils.Pref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<ModelHome> arrayList;
    private ModelHome modelHome;
    private RecyclerView recyclerView;
    private AdapterHome adapterHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerviewHome);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.context, 2));
        Pref.setString("day", new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date()), this.context);
        setData();
    }

    private void setData() {
        this.arrayList = new ArrayList<>();
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Monday");
        this.modelHome.setDayValue(getString(R.string.dayMonday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Tuesday");
        this.modelHome.setDayValue(getString(R.string.dayTuesday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Wednesday");
        this.modelHome.setDayValue(getString(R.string.dayWednesday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Thursday");
        this.modelHome.setDayValue(getString(R.string.dayThursday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Friday");
        this.modelHome.setDayValue(getString(R.string.dayFriday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayName("Saturday");
        this.modelHome.setDayValue(getString(R.string.daySaturday));
        this.arrayList.add(this.modelHome);
        this.modelHome = new ModelHome();
        this.modelHome.setImageView(String.valueOf(R.drawable.app_icon));
        this.modelHome.setDayValue("Sunday");
        this.modelHome.setDayValue(getString(R.string.daySunday));
        this.arrayList.add(this.modelHome);

        adapterHome = new AdapterHome(HomeActivity.this, arrayList);
        recyclerView.setAdapter(this.adapterHome);
    }
}