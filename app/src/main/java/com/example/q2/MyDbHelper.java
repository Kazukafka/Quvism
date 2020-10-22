package com.example.q2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(Context context){
        super(context, null, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(name TEXT NOT NULL, age TEXT);");

        //insert data
        db.execSQL("INSERT INTO person(name, age) values('john', 18);");
        db.execSQL("INSERT INTO person(name, age) values('eric', 20);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}