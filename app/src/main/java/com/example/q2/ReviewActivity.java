package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ReviewActivity extends AppCompatActivity {

    private LinearLayout laylay;

    private Timer Count;
    private TimerTask TimerTask;
    private android.os.Handler handle = new android.os.Handler();
    private int count=5;
    private TextView countTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        countTxt = (TextView)findViewById(R.id.countSecond_txt);
        setTitle("Recheck Your Mistakes");
        laylay = (LinearLayout)findViewById(R.id.laylay);
        laylay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        countSecond();
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
        //タイマーを停止
        Count.cancel();
        //タイマーを破棄
        Count = null;
    } else {    //timer1が停止していた時
        //新しいタイマーを生成
        Count = new Timer();
        //タイマー作動時の動作を新規に登録
        TimerTask = new Task1();
        //1秒後に1秒おきにTimerTaskの処理を実行するタイマーを起動
        Count.schedule(TimerTask, 1000, 1000);
    }
    }
    //ここでタイマー作動時の動作をプログラムしている
    public class Task1 extends TimerTask {
        @Override
        public void run() {
            //メインスレッドに情報を送る
            handle.post(new Runnable() {
                @Override
                public void run() {
                    //タイマー作動時の実際の動作（カウントアップ）
                    if (count == 0){
                        countTxt.setText("Stop");
                        flipAnimation();
                    } else {
                        count -= 1;
                        countTxt.setText(String.valueOf(count));
                    }
                }
            });
        }
    }

}