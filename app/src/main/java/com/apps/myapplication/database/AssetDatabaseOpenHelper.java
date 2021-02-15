package com.apps.myapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssetDatabaseOpenHelper {
    private static final String DB_NAME = "my_db_cal.sqlite";
    private Context context;

    public AssetDatabaseOpenHelper(Context context2) {
        this.context = context2;
    }

    @SuppressLint("WrongConstant")
    public SQLiteDatabase openDatabase() {
        File databasePath = this.context.getDatabasePath(DB_NAME);
        if (!databasePath.exists()) {
            try {
                Context context2 = this.context;
                SQLiteDatabase openOrCreateDatabase = context2.openOrCreateDatabase(DB_NAME, 0, (SQLiteDatabase.CursorFactory) null);
                if (openOrCreateDatabase != null) {
                    openOrCreateDatabase.close();
                }
                copyDatabase(databasePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SQLiteDatabase.openDatabase(databasePath.getPath(), (SQLiteDatabase.CursorFactory) null, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    private void copyDatabase(File file) throws IOException {
        InputStream open = this.context.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[1024];
        while (open.read(bArr) > 0) {
            fileOutputStream.write(bArr);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        open.close();
    }
}
