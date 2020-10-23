package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    public TestOpenHelper helper;
    public SQLiteDatabase db;

    private LinearLayout showLayout2;
    private LinearLayout showLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistakes);

        //showLayout1 = (LinearLayout)findViewById(R.id.layout1);
        //showLayout2 = (LinearLayout)findViewById(R.id.layout2);

        //showLayout1.setVisibility(View.VISIBLE);
        //showLayout2.setVisibility(View.INVISIBLE);

        final Button readButton = findViewById(R.id.button_read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readData();
            }
        });

        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDatabase("testdbdb");
                //readButton.setVisibility(View.GONE);
                //list.setVisibility(View.GONE);
                showLayout1 = (LinearLayout)findViewById(R.id.layout1);
                //showLayout2 = (LinearLayout)findViewById(R.id.layout2);
                showLayout1.setVisibility(View.INVISIBLE);
                //showLayout2.setVisibility(View.VISIBLE);
            }
        });
    }

    public void readData(){

        ListView list = findViewById(R.id.listView);
        ArrayList labelList = new ArrayList();

        CustomAdapter mAdapter = new CustomAdapter(this, 0, labelList);
        list.setAdapter(mAdapter);
        list.setDivider(null);

        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");
        Cursor cur = db.query(
                "testdbdb",
                new String[] { "estonian", "english" },
                null,
                null,
                null,
                null,
                null
        );
        cur.moveToFirst();

        for(int i=1; i<=20; i++){
            labelList.add(cur.getString(0));
            labelList.add(cur.getString(1));
            cur.moveToNext();
        }
        //↓Never Forget to Close Cursor
        cur.close();
    }

    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+ "testdbdb";
        db.execSQL(clearDBQuery);
    }
}