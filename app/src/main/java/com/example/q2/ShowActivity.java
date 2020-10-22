package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.StaleDataException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    public TestOpenHelper helper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Button readButton = findViewById(R.id.button_read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });

        Button deleteButton = findViewById(R.id.button_read2);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper = new TestOpenHelper(getApplicationContext());
                db = helper.getReadableDatabase();
                db.delete("testdbdb", null, new String[]{});
                readData();
                //clearDatabase("testdbdb");
            }
        });
    }

    public void readData(){
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");
        Cursor cursor = db.query(
                "testdbdb",
                new String[] { "estonian", "english" },
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            sbuilder.append(cursor.getString(0));
            sbuilder.append(": ");
            sbuilder.append(cursor.getString(1));
            sbuilder.append("\n");
            cursor.moveToNext();
        }

        // 忘れずに！
        cursor.close();

        TextView textView = findViewById(R.id.text_view);
        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }

    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+ "testdbdb";
        db.execSQL(clearDBQuery);
    }
}