package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private Datahelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new Datahelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(Datahelper.SUBJECT, name);
        contentValue.put(Datahelper.DESC, desc);
        database.insert(Datahelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { Datahelper._ID, Datahelper.SUBJECT, Datahelper.DESC };
        Cursor cursor = database.query(Datahelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Datahelper.SUBJECT, name);
        contentValues.put(Datahelper.DESC, desc);
        int i = database.update(Datahelper.TABLE_NAME, contentValues, Datahelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(Datahelper.TABLE_NAME, Datahelper._ID + "=" + _id, null);
    }
}