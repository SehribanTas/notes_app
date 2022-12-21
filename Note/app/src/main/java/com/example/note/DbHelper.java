package com.example.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class DbHelper extends SQLiteOpenHelper {


    public DbHelper( Context context) {
        super(context,"tasks",null,1);//VERİTABANI ADI BU YAPICI FONK İÇİNE YAZILIR İSİM --TASKS
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//TABLODAKİ ALAN İSİMLERİ BURDA TANIMLARNIR.
        db.execSQL("CREATE TABLE tasks("+
                "task_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task TEXT," +
                "dayOfMonth TEXT," +
                "month TEXT," +
                "year TEXT," +
                "minute TEXT," +
                "hourOfDay TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
}
