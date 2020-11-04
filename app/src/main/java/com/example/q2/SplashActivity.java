package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.image_view);
        startScaling();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, BeginActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    private void startScaling(){
        //(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1.0f, 4.0f, 1.0f,4.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //time
        scaleAnim.setDuration(1000);
        //repeat
        scaleAnim.setRepeatCount(0);
        //Show it after
        scaleAnim.setFillAfter(true);
        //Begin
        imageView.startAnimation(scaleAnim);
    }
}