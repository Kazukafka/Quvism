package com.euassfdevelopmentproject.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MistakesActivity extends AppCompatActivity {

    public TestOpenHelper helper;
    public SQLiteDatabase db;

    private LinearLayout showLayout2;
    private LinearLayout showLayout1;

    private TextView nodatatxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);
        setTitle("Mistakes");

        readData();

        Button btnBTM = findViewById(R.id.btnBackToMenu);
        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDatabase("mistakesDB");
                moveLayout();
            }
        });

        Button btn_to_flip = findViewById(R.id.button_to_flip);
        btn_to_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        btnBTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), BeginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void readData(){
        ListView list = findViewById(R.id.listView);
        ArrayList labelList = new ArrayList();
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");
        Cursor cur = db.query(
                "mistakesDB",
                new String[] { "estonian", "english" },
                null,
                null,
                null,
                null,
                null
        );

        //How many raw lines in the database?
        int rawCount = (int) DatabaseUtils.queryNumEntries(db, "mistakesDB");
        cur.moveToFirst();

        for(int i=1; i<=rawCount; i++){
            labelList.add(cur.getString(0));
            labelList.add(cur.getString(1));
            cur.moveToNext();
        }

        //↓Never Forget to Close Cursor
        cur.close();

        CustomAdapter mAdapter = new CustomAdapter(this, 0, labelList);
        list.setAdapter(mAdapter);
        list.setDivider(null);
    }

    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+ "mistakesDB";
        db.execSQL(clearDBQuery);
    }

    public void moveLayout() {
        showLayout1 = (LinearLayout)findViewById(R.id.layout1);
        //↓Layout2 must be defined here
        showLayout2 = (LinearLayout)findViewById(R.id.layout2);
        showLayout1.setVisibility(View.GONE);
    }
}