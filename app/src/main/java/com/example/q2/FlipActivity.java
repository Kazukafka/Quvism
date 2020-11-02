package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FlipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        final ImageView imageView = findViewById(R.id.flip2);
        final TextView textView = findViewById(R.id.flipTest);

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        final Button button = findViewById(R.id.bbb);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int a = 1;
                if (a == 1){
                    textView.setText("ABCD");
                    a = 2;
                } else {
                    textView.setText("LLLLLLLLLLLLLLL");
                }
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imageView.setImageResource(R.drawable.test_c_image);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });
        /*
        imageView.setOnClickListener(new View.OnClickListener() {
            int a = 1;
            @Override
            public void onClick(View v) {
                if (a == 1){
                    textView.setText("ABCD");
                    a = 2;
                } else {
                    textView.setText("LLLLLLLLLLLLLLL");
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int a = 1;
                if (a == 1){
                    textView.setText("ABCD");
                    a = 2;
                } else {
                    textView.setText("LLLLLLLLLLLLLLL");
                }

                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imageView.setImageResource(R.drawable.test_c_image);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

         */
    }
}