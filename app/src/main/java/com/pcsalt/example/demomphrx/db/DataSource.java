package com.pcsalt.example.demomphrx.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pcsalt.example.demomphrx.model.WebUrl;

/**
 * This class handles the database operations, like insert, get and check if data is available.
 */
public class DataSource {
    /**
     * Instance of SQLiteDatabase to operate on.
     */
    private SQLiteDatabase database;
    /**
     * Instance of SQLiteOpenHelper.
     */
    private DbHelper dbHelper;
    /**
     * String array, which contains all the columns of the database
     */
    private String[] allColumns = {DbHelper.ID, DbHelper.WEB_URL, DbHelper.DESCRIPTION};

    /**
     * Constructor to create instance of the database. By calling this constructor, a database is
     * created with the tables mentioned in the custom class extending
     * {@link android.database.sqlite.SQLiteOpenHelper#onCreate(SQLiteDatabase)}.
     *
     * @param context a reference of Context
     */
    public DataSource(@NonNull Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Calling this method opens the database connection and gets the database with write lock.
     */
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Calling this method closes the database connection.
     */
    public void close() {
        if (database != null) {
            database.close();
        }
    }

    /**
     * Utility method to check if data is available in the database against a webUrlId.
     *
     * @param webUrlId check if data is available for this webUrlId
     * @return true if data is available, false otherwise
     */
    public boolean exists(String webUrlId) {
        return getWebUrl(webUrlId) != null;
    }

    /**
     * Utility method to insert data into the database.
     *
     * @param webUrl   model of {@link WebUrl}
     * @param webUrlId id of webUrl
     * @return insertId of the row inserted
     */
    public long insertWebUrl(@NonNull WebUrl webUrl, String webUrlId) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.WEB_URL_ID, webUrlId);
        values.put(DbHelper.WEB_URL, webUrl.getWebUrl());
        values.put(DbHelper.DESCRIPTION, webUrl.getDescription());

        return database.insert(DbHelper.TABLE, null, values);
    }

    /**
     * Utility method to get the {@link WebUrl} content from the database against selectedUrlId.
     *
     * @param selectedUrlId id of the selected url
     * @return {@link WebUrl} instance containing the webUrl and description, null if data is not found
     */
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
