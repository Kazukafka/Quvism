package com.example.q2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MistakesActivity extends AppCompatActivity {

    public TestOpenHelper helper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);

        ListView list = (ListView)findViewById(R.id.listView);
        ArrayList labelList = new ArrayList();
        for(int i=1; i<=20; i++){
            labelList.add("List Item "+i);
        }

        CustomAdapter mAdapter = new CustomAdapter(this, 0, labelList);
        list.setAdapter(mAdapter);
        list.setDivider(null);
    }
}