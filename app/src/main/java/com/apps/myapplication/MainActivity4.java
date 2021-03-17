package com.apps.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {
    List<MainData> dataList = new ArrayList();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        this.listView = findViewById(R.id.list_view);


        PackageManager manager = getPackageManager();
        for (ApplicationInfo info : manager.getInstalledApplications(PackageManager.GET_META_DATA)) {
            if ((info.flags & 1) == 0) {
                MainData data = new MainData();
                data.setName(info.loadLabel(manager).toString());
                data.setPackageName(info.packageName);
                data.setLogo(info.loadIcon(manager));
                this.dataList.add(data);
            }
        }
        this.listView.setAdapter(new BaseAdapter() {
            public int getCount() {
                return MainActivity4.this.dataList.size();
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = MainActivity4.this.getLayoutInflater().inflate(R.layout.list_row_item, null);
                MainData data = MainActivity4.this.dataList.get(position);
                ((ImageView) view.findViewById(R.id.iv_app_logo)).setImageDrawable(data.getLogo());
                ((TextView) view.findViewById(R.id.tv_app_name)).setText(data.getName());
                view.findViewById(R.id.bt_uninstall).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.UNINSTALL_PACKAGE");
                        intent.setData(Uri.parse("package:" + dataList.get(position).getPackageName()));
                        intent.putExtra("android.intent.extra.RETURN_RESULT", true);
                        startActivityForResult(intent, 100);
                    }
                });
                return view;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            Toast.makeText(getApplicationContext(), "App Uninstall Successfully ...", 1).show();
            recreate();
        }
    }
}