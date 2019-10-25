package com.example.demodata_2_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper   extends SQLiteOpenHelper

{
    public static final String TABLE_NAME = "notes_data";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String DATABASE_NAME = "notes_data_db";


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOTE + " TEXT,"

                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }


    public long insertNote(String note) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Note getNote(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_TIMESTAMP},COLUMN_ID + "=?",new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)), cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));

        cursor.close();

        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }


    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",new String[]{String.valueOf(note.getId())});
        db.close();
    }
}