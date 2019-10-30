package com.iatneh.mynotesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iatneh.mynotesapp.model.Note;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DB_Name = "MyNoteApp";
    static final int DB_Version = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    public ArrayList<Note> getAllNote() {

        ArrayList<Note> list = new ArrayList<>();
        String MY_TABLE = "Notes";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {

                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        Note obj = new Note();

                        obj.setId(cursor.getInt(0));
                        obj.setTitle(cursor.getString(1));
                        obj.setContent(cursor.getString(2));
                        obj.setDate(cursor.getString(3));
                        obj.setColor(cursor.getInt(4));

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }

        return list;
    }

    public boolean addNote(String title, String content, String date, int color) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("date", date);
        contentValues.put("color", color);
        db.insert("Notes", null, contentValues);
        db.close();

        return true;
    }

    public boolean updateNote(int id, String title, String content, String date, int color)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("date",date);
        contentValues.put("color",color);

        db.update("Notes", contentValues,"id=" + id, null);

        return true;
    }

    public boolean deleteNote(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Notes","id=?",new String[]{(String.valueOf(id))});

        return true;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String Notes = "CREATE TABLE Notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, date VARCHAR, color INTEGER);";

        db.execSQL(Notes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String Notes = "DROP TABLE IF EXISTS Notes";

        db.execSQL(Notes);

        onCreate(db);
    }
}
