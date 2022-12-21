package com.example.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

class Tasksdao{///şimdilik parametre olarak task alır .date ve hour eklersem parametrelere date ve hour da eklenmeli

    public ArrayList<Tasks>  getTasks(DbHelper dbHelper) {
        ArrayList<Tasks> tasksArrayList = new ArrayList<>();
        SQLiteDatabase dbx = dbHelper.getWritableDatabase(); //veritabanı nesnei
        Cursor c = dbx.rawQuery("SELECT *FROM tasks", null);
        while (c.moveToNext()) {
            Tasks task= new Tasks(c.getInt(c.getColumnIndex("task_id"))
                    ,c.getString(c.getColumnIndex("task"))
                    );
            tasksArrayList.add(task);
        }
        return tasksArrayList; }


    public void taskAdd(DbHelper dbHelper, String task
            ,String dayOfMonth,String month,String year,String minute,String hourOfDay
    ){//ekleme işlemini gerçekleştirir.task ve dbhelper alır.
        SQLiteDatabase dbx = dbHelper.getWritableDatabase();
        ContentValues degerler = new ContentValues();

        degerler.put("task",task);
        degerler.put("dayOfMonth",dayOfMonth);
        degerler.put("month",month);
        degerler.put("year",year);
        degerler.put("minute",minute);
        degerler.put("hourOfDay",hourOfDay);

        dbx.insertOrThrow("tasks",null,degerler);
        dbx.close();

    }
//GÜNCELLEME FONKSİYONU

    public void updateTask(DbHelper dbHelper,int task_id,String task,String dayOfMonth,String hourOfDay){
        SQLiteDatabase dbx = dbHelper.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put("task",task);
        degerler.put("dayOfMonth",dayOfMonth);
        degerler.put("hourOfDay",hourOfDay);
        dbx.update("tasks",degerler,"task_id=?",new String[]{String.valueOf(task_id)});
        dbx.close();

    }
///SİLME FONKSİYONU
    public void deleteTask(DbHelper dbHelper, int task_id){

        SQLiteDatabase dbx = dbHelper.getWritableDatabase();
        dbx.delete("tasks","task_id=?", new String[]{String.valueOf(task_id)});
        dbx.close();
    }





}
