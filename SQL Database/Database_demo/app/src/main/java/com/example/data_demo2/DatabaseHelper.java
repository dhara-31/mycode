package com.example.data_demo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "login_email.db";
    public static final String TABLE_NAME="email_login";
    public static final String COL_1="ID";
    public static final String COL_2="NAME ";
    public static final String COL_3="Email";
    public static final String COL_4="Password";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,EMAIL TEXT, INREGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public long addUser(String name,String email,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        long result = db.insert(TABLE_NAME,null,contentValues);

            return result;
    }

    public Cursor getAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }
    public boolean updateData(String email,String password){
        Log.e(TAG, "updateData Email:>>>>>>>>>> "+email );
        Log.e(TAG, "updateData Password: "+password );
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,password);
        db.update(TABLE_NAME,contentValues, COL_3+"=?",new String[]{ email});
        return true;
    }
    public Integer deleteData(String email){

        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(TABLE_NAME,"Email=?",new String[]{ email });
    }

    public boolean checkuser(String email,String password){
        String[] columns ={COL_1};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=COL_3 + " = ?" + " AND " + COL_4 + " = ?";

        String[] selectionargs ={email,password};
        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionargs,null,null,null);

        int cursorcount=cursor.getCount();

        cursor.close();
        db.close();
        if (cursorcount>0)
        {
            return true;
        }
        return false;

    }
}