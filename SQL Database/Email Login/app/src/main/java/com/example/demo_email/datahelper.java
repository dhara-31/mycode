package com.example.demo_email;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class datahelper extends SQLiteOpenHelper {
    SQLiteDatabase db;


    public static final String DATABASE_NAME = "email_info.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "profile";
    public static final String COL_ID = "id";
    public static final String NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "mobile";


    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +NAME + " TEXT, "+ COL_EMAIL + " TEXT, " + COL_PASSWORD + " TEXT, " + COL_MOBILE + " TEXT " + ")";

    public datahelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}