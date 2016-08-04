package com.pcsalt.example.demomphrx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pcsalt.example.demomphrx.model.WebUrl;

public class DataSource {
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = {DbHelper.ID, DbHelper.WEB_URL, DbHelper.DESCRIPTION};

    public DataSource(@NonNull Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public boolean exists(String webUrlId) {
        return getWebUrl(webUrlId) != null;
    }

    public long insertWebUrl(@NonNull WebUrl webUrl, String webUrlId) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.WEB_URL_ID, webUrlId);
        values.put(DbHelper.WEB_URL, webUrl.getWebUrl());
        values.put(DbHelper.DESCRIPTION, webUrl.getDescription());

        return database.insert(DbHelper.TABLE, null, values);
    }

    @Nullable
    public WebUrl getWebUrl(String selectedUrlId) {
        Cursor cursor = database.query(DbHelper.TABLE, allColumns, DbHelper.WEB_URL_ID + " = \"" + selectedUrlId + "\"", null, null, null, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            WebUrl webUrl = new WebUrl();
            webUrl.setWebUrl(cursor.getString(cursor.getColumnIndex(DbHelper.WEB_URL)));
            webUrl.setDescription(cursor.getString(cursor.getColumnIndex(DbHelper.DESCRIPTION)));
            cursor.close();
            return webUrl;
        }
        return null;
    }
}
