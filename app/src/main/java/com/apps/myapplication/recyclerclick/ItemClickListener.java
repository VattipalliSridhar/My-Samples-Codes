package com.apps.myapplication.recyclerclick;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
