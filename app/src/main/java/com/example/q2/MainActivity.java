package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int qCount = 1;
    static final private int QUIZ_COUNT = 10;

    ArrayList<ArrayList<String>> questionArray1 = new ArrayList<>();

    //Test by SharedPriffferences
    String[][] qDB = {
            // {"Estonian", "RightAnswer", "Choice_1", "Choice _２", "Choice_３"}
            {"Tere", "Hello", "Thanks", "Oh my god", "No"},
            {"Aitah", "Thank you", "Yes", "Well Done", "Not really"},
            {"Homikst", "Morning","ABC", "Doremi", "Evening"},
            {"Head ööd!", "Good Evening", "How are you?", "Fine", "Soso"},
            {"Nägemist!", "Bye!","See you", "Good", "Never"},
            {"Maga hästi!", "Sleep well","Hey", "Never mind", "right"},
            {"Homseni!", "See you tomorrow", "ABC", "fine", "Soso"},
            {"Head ööd!", "Good night", "No", "Yes", "PPAP"},
            {"Tere tulemast!", "Welcome! (informal)", "Hell", "Yes", "It is easy"},
            {"Tere tulemast tagasi!", "Welcome back!", "OK", "left", "right"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        //Make questionArray from qDB
        for (String[] quizDatum : qDB) {

            //Prepare the nw array
            ArrayList<String> tmpArray = new ArrayList<>();

            //Add QuestionData
            tmpArray.add(quizDatum[0]);
            tmpArray.add(quizDatum[1]);
            tmpArray.add(quizDatum[2]);
            tmpArray.add(quizDatum[3]);
            tmpArray.add(quizDatum[4]);

            //Add tmpArray to the questionArray
            questionArray1.add(tmpArray);
        }

        showNextQuiz();

    }

    public void showNextQuiz() {
        //Reflesh quizCount
        countLabel.setText(getString(R.string.quiz_count, qCount));

        //Ged Random numbers
        Random random = new Random();
        int randomNum = random.nextInt(questionArray1.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        //Get one from questionArray by using randomNum
        ArrayList<String> question = questionArray1.get(randomNum);


        //Show the question
        questionLabel.setText(question.get(0));

        //set the right answer
        rightAnswer = question.get(1);

        //Deleter pre-question
        question.remove(0);

        //Shuffle
        Collections.shuffle(question);

        // Show the buttons
        answerBtn1.setText(question.get(0));
        answerBtn2.setText(question.get(1));
        answerBtn3.setText(question.get(2));
        answerBtn4.setText(question.get(3));

        //Delete question from this array
        questionArray1.remove(randomNum);
    }


    public void checkAnswer(View view) {

        //Which button has been pushed ?
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "Correct!";
            rightAnswerCount++;
        } else {
            alertTitle = "Wrong...";
        }

        //Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (qCount == QUIZ_COUNT) {
                    //Move to the resultActivity
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("Count_right_ans", rightAnswerCount);
                    startActivity(intent);

                } else {
                    qCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
