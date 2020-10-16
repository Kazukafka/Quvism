package com.example.q2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    int MY_DATA_CHECK_CODE = 1000;
    TextToSpeech textToSpeech;

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

    TextView ttsText;

    ArrayList<ArrayList<String>> questionArray1 = new ArrayList<>();

    //Test by SharedPriffferences
    /*
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
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!3", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!3", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!3", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!3", "Welcome back!", "OK", "left", "right"},

    };

     */

    String[][] qDB1 = {
            {"Tere", "Hello", "Thanks", "Oh my god", "No"},
            {"Aitah", "Thank you", "Yes", "Well Done", "Not really"},
            {"Homikst", "Morning","ABC", "Doremi", "Evening"},
            {"Head ööd!", "Good Evening", "How are you?", "Fine", "Soso"},
            {"Nägemist!", "Bye!","See you", "Good", "Never"},
            {"Maga hästi!", "Sleep well","Hey", "Never mind", "right"},
            {"Homseni!", "See you tomorrow", "ABC", "fine", "Soso"},
            {"Head ööd!", "Good night", "No", "Yes", "PPAP"},
            {"Tere tulemast!", "Welcome! (informal)", "Hell", "Yes", "It is easy"},
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
            {"Tere tulemast tagasi!2", "Welcome back!", "OK", "left", "right"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton speakbutton;

        textToSpeech = new TextToSpeech(getApplicationContext(), this);

        ImageButton button01 = findViewById(R.id.image_button_setting);

        if(savedInstanceState == null){
            button01.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // BackStackを設定
                    fragmentTransaction.addToBackStack(null);

                    // パラメータを設定
                    fragmentTransaction.replace(R.id.container,
                            TestFragment.newInstance("Fragment"));
                    fragmentTransaction.commit();
                }

            });
        }

        ttsText = (TextView) findViewById(R.id.questionLabel);
        speakbutton = (ImageButton) findViewById(R.id.image_button_speak);
        speakbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = ttsText.getText().toString();
                if(text.length() > 0){
                    textToSpeech.setLanguage(new Locale("et-EE"));
                    textToSpeech.speak(text,TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, MY_DATA_CHECK_CODE);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        //Make questionArray from qDB
        for (String[] quizDatum : qDB1) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                textToSpeech = new TextToSpeech(this, this);
            } else {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            }
        }}

    public void onInit(int i) {
        if(i == textToSpeech.SUCCESS){
            //Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        } else if (i == textToSpeech.ERROR){
            //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void showNextQuiz() {
        //Reflesh quizCount
        countLabel.setText(getString(R.string.quiz_count, qCount));

        //Ged Random numbers
        Random random = new Random();
        int randomNum = random.nextInt(questionArray1.size());

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
