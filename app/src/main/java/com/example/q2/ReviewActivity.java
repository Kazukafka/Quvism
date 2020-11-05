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

    private int count = 3;

    //Get Data from SQLite
    public TestOpenHelper helper;
    public SQLiteDatabase db;

    private LinearLayout laylay;

    private Timer Count;
    private TimerTask TimerTask;
    private android.os.Handler handle = new android.os.Handler();
    private TextView countTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        countTxt = (TextView)findViewById(R.id.countSecond_txt);
        setTitle("Recheck Your Mistakes");
        laylay = (LinearLayout)findViewById(R.id.laylay);
        countSecond();
        //animationReset();
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

    public void countSecond(){
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
        //1秒後に1秒おきにTimerTaskの処理を実行するタイマーを起動
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
                    //Count Up!

                    /*
                    for (; count == 0; count--){
                        countTxt.setText(String.valueOf(count));
                    }

                     */

                    /*
                    for (; count == 0; count --){
                        countTxt.setText(String.valueOf(count));
                    }

                     */



                    while (count > 0){
                        //count --;
                        countTxt.setText(String.valueOf(count));
                        if (count == 0){
                            countTxt.setText("Stop");
                            flipAnimation();
                            break;
                        } else {
                            count -= 1;
                            countTxt.setText(String.valueOf(count));
                        }
                    }



                    /*
                    if (count==0){
                        countTxt.setText("Stop");
                        while(count>0){
                            flipAnimation();
                            break;
                        }
                    }else{
                        count -= 1;
                        countTxt.setText(String.valueOf(count));
                    }

                     */





                    /*
                    int x = 100;
                    while (x > 0){
                        x --;
                        countTxt.setText(String.valueOf(count));
                        if (x == 0){
                            countTxt.setText("Stop");
                            flipAnimation();
                            break;
                        } else {
                            x -= 1;
                            countTxt.setText(String.valueOf(count));
                        }
                    }

                     */

                }
            });
        }
    }

    private void animationReset() {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(laylay, "scaleX", 0f, 0f);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(laylay, "scaleX", 0f, 0f);
    }


    /*
    public void readData(){
        ListView list = findViewById(R.id.listView);
        ArrayList labelList = new ArrayList();
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

     */
}