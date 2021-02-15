package com.apps.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;

import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.myapplication.database.DbHelper;
import com.apps.myapplication.model.EventsModel;
import com.apps.myapplication.model.PanchangamModel;
import com.apps.myapplication.utils.Golble;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalenderActivity extends AppCompatActivity implements CompactCalendarView.CompactCalendarViewListener {


    TimeZone timeZone;

    ArrayList<EventsModel> eventsModelArrayList;
    ArrayList<PanchangamModel> panchangamModelArrayList;
    CompactCalendarView compactCalendarView;
    String teluguMonthName;

    TextView mDayNameTV;
    TextView mDayNumberTV;
    TextView mFestivNameTV;
   // TextView mPanchangamDataTV;
    //TextView monthYearTextView;
    ImageView nextMonthIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        this.timeZone = TimeZone.getDefault();

        this.mDayNumberTV = (TextView) findViewById(R.id.dayNumberTV);
        this.mDayNameTV = (TextView) findViewById(R.id.dayNameTv);
        this.mFestivNameTV = (TextView) findViewById(R.id.festivalNameTV);
        Golble.setTelegu(this, this.mDayNameTV);
        Golble.setTelegu(this, this.mFestivNameTV);
       // Golble.setTelegu(this, this.mPanchangamDataTV);


        setSelectedDayAllData((Date) null);

         compactCalendarView = (CompactCalendarView) findViewById(R.id.compact_calendar_view);
        compactCalendarView.setLocale(this.timeZone, new Locale("te"));
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setListener(this);

        addCalenderEvents();
    }

    private void addCalenderEvents() {
        eventsModelArrayList = DbHelper.getCalenderEvents(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Iterator<EventsModel> it = this.eventsModelArrayList.iterator();
        while (it.hasNext()) {
            try {
                compactCalendarView.addEvent(new Event(SupportMenu.CATEGORY_MASK, simpleDateFormat.parse(it.next().getDate()).getTime()), false);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDayClick(Date date) {
        List<Event> events = this.compactCalendarView.getEvents(date);
        updateDaySpecialFields(date.toString());
//        Log.e("TAG", "Day was clicked==========: " + date + " with events " + events.get(0).getData().toString());

    }

    @Override
    public void onMonthScroll(Date firstDayOfNewMonth) {

    }

    private void updateDaySpecialFields(String str) {
        Date date;
        String[] split = str.split(" ");
        panchangamModelArrayList = DbHelper.getPanchangamTableDetails(this);
        Log.e("TAG", "split array=========" + String.valueOf(split.toString()));
        try {
            date = new SimpleDateFormat("E MMM dd yyyy").parse(split[0] + " " + split[1] + " " + split[2] + " " + split[5]);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        System.out.println(date);
        Log.e("TAG", "date ========" + date);
        Calendar instance = Calendar.getInstance();
        if (date != null) {
            instance.setTime(date);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(instance.get(1));
        sb.append("-");
        sb.append(addZeroBeforeNumber("" + (instance.get(2) + 1)));
        sb.append("-");
        sb.append(addZeroBeforeNumber("" + instance.get(5)));
        String sb2 = sb.toString();
        Log.e("TAG", "formatedDate========" + sb2);
        teluguMonthName = Golble.getTeluguMonthName(split[1]);
        Iterator<PanchangamModel> it = this.panchangamModelArrayList.iterator();
        String str2 = "";
        String str3 = str2;
        boolean z = false;
        while (it.hasNext()) {
            PanchangamModel next = it.next();
            if (next.getpDate().equalsIgnoreCase(sb2)) {
                String str4 = next.getpFTwo();
               // TextView textView = this.monthYearTextView;
              //  textView.setText("శ్రీ " + next.getpTeluguYear() + " నామ సంవత్సరం" + "\n" + this.teluguMonthName + " , " + instance.get(1));
                str2 = str4;
                z = true;
            }
        }
        if (!z) {
           // TextView textView2 = this.monthYearTextView;
           // textView2.setText("" + this.teluguMonthName + " , " + instance.get(1));
        }
        TextView textView3 = this.mDayNumberTV;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        sb3.append(addZeroBeforeNumber("" + instance.get(5)));
        textView3.setText(sb3.toString());
        TextView textView4 = this.mDayNameTV;
        textView4.setText("" + Golble.getTeluguDayName(split[0]));
        if (str2.length() != 0) {
            TextView textView5 = this.mFestivNameTV;
            textView5.setText("" + str2);
        } else {
            this.mFestivNameTV.setText("");
        }
    }

    public void setSelectedDayAllData(Date date) {
        if (date == null) {
            date = new Date();
        }
        String str = (String) DateFormat.format("E", date);
        String str2 = (String) DateFormat.format("dd", date);
        String str3 = (String) DateFormat.format("MMMM", date);
        String str4 = (String) DateFormat.format("yyyy", date);
        boolean z = false;
        for (int i = 0; i < Golble.monthsInEnglish.length; i++) {
            if (((String) DateFormat.format("MMM", date)).equalsIgnoreCase(Golble.monthsInEnglish[i])) {
                this.teluguMonthName = Golble.monthsInTelugu[i];
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append("-");
        sb.append(addZeroBeforeNumber("" + ((String) DateFormat.format("MM", date))));
        sb.append("-");
        sb.append(addZeroBeforeNumber("" + ((String) DateFormat.format("dd", date))));
        String sb2 = sb.toString();
        if (this.panchangamModelArrayList == null) {
            this.panchangamModelArrayList = DbHelper.getPanchangamTableDetails(this);
        }
        Iterator<PanchangamModel> it = this.panchangamModelArrayList.iterator();
        String str5 = "";
        String str6 = str5;
        while (it.hasNext()) {
            PanchangamModel next = it.next();
            if (next.getpDate().equalsIgnoreCase(sb2)) {
                //TextView textView = this.monthYearTextView;
               // textView.setText("శ్రీ " + next.getpTeluguYear() + " నామ సంవత్సరం" + "\n" + this.teluguMonthName + " , " + str4);
                String str7 = next.getpFTwo();
                //str6 = constructPanchangamData(next);
                str5 = str7;
                z = true;
            }
        }
        if (!z) {
           // TextView textView2 = this.monthYearTextView;
           // textView2.setText("" + this.teluguMonthName + " , " + str4);
        }
        TextView textView3 = this.mDayNumberTV;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("");
        sb3.append(addZeroBeforeNumber("" + str2));
        textView3.setText(sb3.toString());
        TextView textView4 = this.mDayNameTV;
        textView4.setText("" + Golble.getTeluguDayName(str));
        if (str5.length() != 0) {
            TextView textView5 = this.mFestivNameTV;
            textView5.setText("" + str5);
        } else {
            this.mFestivNameTV.setText("");
        }
      //  this.mPanchangamDataTV.setText(Html.fromHtml(str6));
    }


    private String addZeroBeforeNumber(String str) {
        if (str.length() != 1) {
            return str;
        }
        return "0" + str;
    }


}