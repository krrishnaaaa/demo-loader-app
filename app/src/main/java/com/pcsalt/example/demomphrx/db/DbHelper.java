package com.pcsalt.example.demomphrx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class creates a table to store the WebUrl information. And provides a reference to the
 * database to to CRUD operations on the table.
 */
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

    /**
     * Called when the instance of the database is created. It contains the code to create table(s).
     * If the database version is upgraded because of change in a table row, then the create
     * statement should not be changed. Instead, next statement should be written to alter, or add
     * the column.
     *
     * @param sqLiteDatabase instance received from the parent class
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                ID + " integer primary key autoincrement, " +
                WEB_URL_ID + " text not null, " +
                WEB_URL + " text not null, " +
                DESCRIPTION + " text not null);");
    }

    /**
     * For now this method does nothing. But, if the database is updated then based on the updation
     * process, we drop the database, and/or call onCreate().
     *
     * @param sqLiteDatabase instance of the database
     * @param oldVersion     previous version of the database application have
     * @param newVersion     new version of the database to be created/updated
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
