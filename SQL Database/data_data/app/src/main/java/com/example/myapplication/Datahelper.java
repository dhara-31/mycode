package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Datahelper extends SQLiteOpenHelper
{

    public static final String TABLE_NAME = "COUNTRIES";
    public static final String _ID = "_id";
    public static final String SUBJECT = "subject";
    public static final String DESC = "description";
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";


    public Datahelper( Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DESC + " TEXT);";
         db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

}