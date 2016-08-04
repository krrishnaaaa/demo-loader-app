package com.pcsalt.example.demomphrx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    static final String TABLE = "url_list";
    static final String ID = "_id";
    static final String WEB_URL_ID = "web_url_id";
    static final String WEB_URL = "web_url";
    static final String DESCRIPTION = "description";

    private static final String DATABASE_NAME = "urls.db";
    private static final int DATABASE_VERSION = 1;

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + " ( " +
                ID + " integer primary key autoincrement, " +
                WEB_URL_ID + " text not null, " +
                WEB_URL + " text not null, " +
                DESCRIPTION + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
