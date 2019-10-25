package com.example.database_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Databasehelper  extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "userManager.db";
    public static final String TABLE_USER = "user";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_USER + " (" +COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +COL_NAME + " TEXT, "+ COL_EMAIL + " TEXT, " + COL_PASSWORD + " TEXT " + ")";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }

    public long addUser(String name, String email,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL,email);
        values.put(COL_PASSWORD,pass);

        long res=  db.insert(TABLE_USER, null, values);
        db.close();

        return  res;
    }



    public List<user> getAllUser() {
        String[] columns = {
                COL_ID,
                COL_EMAIL,
                COL_NAME,
                COL_PASSWORD
        };
            String sortorder=COL_NAME;
            List<user>userList=new ArrayList<user>();

            SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_USER,columns,null,null,null,null,sortorder);
        Log.e("copubntfgvh", "getAllUser: "+cursor.getCount() );
        if (cursor.moveToFirst()){
            do {
                    user user=new user();
                    user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ID))));
                    user.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));

                    userList.add(user);
                }while (cursor.moveToNext());
                cursor.close();
                db.close();
            }
            return userList;
        }
        public void update(user user){

            SQLiteDatabase db=this.getWritableDatabase();

           ContentValues values=new ContentValues();
           values.put(COL_NAME,user.getName());
           values.put(COL_EMAIL,user.getEmail());
           values.put(COL_PASSWORD,user.getPassword());

           db.update(TABLE_USER,values,COL_ID + " = ? ",new String[]{String.valueOf(user.getId())});
            db.close();
    }
    public Integer delete(user user){
        Log.e("yes", "delete: "+user.getId());
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(TABLE_USER,COL_ID+"=?",new String[]{String.valueOf(user.getId())});
    }

    public boolean checkuser(String email,String password){
        String[] columns ={COL_ID};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=COL_EMAIL + " = ?" + " AND " + COL_PASSWORD + " = ?";

        String[] selectionargs ={email,password};
        Cursor cursor=db.query(TABLE_USER,columns,selection,selectionargs,null,null,null);

        int cursorcount=cursor.getCount();

        cursor.close();
        db.close();
        if (cursorcount>0){
            return true;
        }
            return false;
    }


}