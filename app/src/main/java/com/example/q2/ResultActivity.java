package com.example.q2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
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

        btnTOMIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MistakeActivity.class);
                startActivity(intent);
            }
        });
        btnBTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), StartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void returnTop(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //StartActivity is Capital, so this is just a command
        startActivity(intent);
    }
}