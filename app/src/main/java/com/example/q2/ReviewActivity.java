package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ReviewActivity extends AppCompatActivity {

    //Get Data from SQLite
    public TestOpenHelper helper;
    public SQLiteDatabase db;

    private LinearLayout laylay;

    private Timer Count;
    private TimerTask TimerTask;
    private android.os.Handler handle = new android.os.Handler();
    private TextView countTxt;
    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        countTxt = (TextView)findViewById(R.id.countSecond_txt);
        setTitle("Recheck Your Mistakes");
        laylay = (LinearLayout)findViewById(R.id.laylay);
        readData();
        countSeconds();
    }

    public void countSeconds(){
        if (Count != null) {
            //Stop Timer
            Count.cancel();
            //Destroy Timer
            Count = null;
        } else {    //if timer is paused
            //Generate new timer
            Count = new Timer();
            //Register action
            TimerTask = new tasuku();
            //Implement timer par 1 second par 1 second
            Count.schedule(TimerTask, 1000, 1000);
        }
    }

    //WHen the timer works
    public class tasuku extends TimerTask {
        @Override
        public void run() {
            //Send info to main thread
            handle.post(new Runnable() {
                @Override
                public void run() {
                    //Timer Timer at the time of timer (count up)
                    count --;
                    //Show current count value in text view
                    countTxt.setText(String.valueOf(count));
                    if (count==0){
                        countTxt.setText("Answer");
                        flipAnimation();
                        TimerTask.cancel();
                        readData();
                    }
                }
            });
        }
    }

    public void flipAnimation(){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(laylay, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(laylay, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oa2.start();
            }
        });
        oa1.start();
    }



    public void readData(){
        TextView txtEstonian = findViewById(R.id.estonian_txt);
        TextView txtEnglish = findViewById(R.id.english_txt);

        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        } else {
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
            txtEstonian.setText(cur.getString(0));
            txtEnglish.setText(cur.getString(1));
            cur.moveToNext();
        }

        //↓Never Forget to Close Cursor
        cur.close();

        /*
        CustomAdapter mAdapter = new CustomAdapter(this, 0, labelList);
        list.setAdapter(mAdapter);
        list.setDivider(null);
         */
    }
}