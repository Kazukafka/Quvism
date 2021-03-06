package com.euassfdevelopmentproject.q2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OutcomeActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "testPackage.MESSAGE";
    public String message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome);
        setTitle("Your Result");

        TextView resultLabel = findViewById(R.id.resultLabel);
        TextView totalScoreLabel = findViewById(R.id.totalScoreLabel);

        //Get number of correct answers
        int score = getIntent().getIntExtra("Count_right_ans", 0);

        //Read & show the total score
        SharedPreferences prefs = getSharedPreferences("Quvism", Context.MODE_PRIVATE);
        int totalSco = prefs.getInt("tScore", 0);

        //Add this score to the total score
        totalSco += score;

        //Show in the TextView
        resultLabel.setText(getString(R.string.result_score, score));
        totalScoreLabel.setText(getString(R.string.result_total_score, totalSco));

        //save the total score
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tScore", totalSco);
        editor.apply();

        Button btnTOMIS = findViewById(R.id.btnToMistakes);
        Button btnBTM = findViewById(R.id.btnBackToMenu);
        Button btnQuizAgain = findViewById(R.id.btnQuizAgain);

        btnTOMIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MistakesActivity.class);
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


        int i = getIntent().getIntExtra("againQuiz", 0);
        String s = String.valueOf(i);

        Intent intent2 = new Intent(getApplication(), QuizActivity.class);
        message2 = intent2.getStringExtra(QuizActivity.EXTRA_MESSAGE2);


        btnQuizAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;

                if (i==1){
                    str ="Academic";
                } else if(i==2){
                    str="Business";
                } else {
                    str="Daily";
                }
                intent2.putExtra(EXTRA_MESSAGE, str);
                startActivity(intent2);
            }
        });
    }
}