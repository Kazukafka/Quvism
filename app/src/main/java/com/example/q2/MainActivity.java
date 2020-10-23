package com.example.q2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    //SQLite Components
    private TestOpenHelper helper;
    private SQLiteDatabase db;

    private LinearLayout mLayout1;
    private LinearLayout mLayout2;

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

    //TextToSpeech
    TextView ttsText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button bbreset;

    //Fragment Data Moveing 1) Create SP in Main 2) Call the sp in Fragmet 3) Edit sp in fragment 4) get data from SP in Main
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
        setContentView(R.layout.activity_main2);

        //Ad Place
        // Test App ID
        MobileAds.initialize(this,
                "ca-app-pub-6500766760315589~2685471571");

        AdView adViewOne = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        adViewOne.loadAd(adRequest1);

        // ad's lifecycle: loading, opening, closing, and so on
        adViewOne.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("debug","Code to be executed when an ad finishes loading.");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("debug","Code to be executed when an ad request fails.");
            }
            @Override
            public void onAdOpened() {
                Log.d("debug","Code to be executed when an ad opens an overlay that covers the screen.");
            }
            @Override
            public void onAdLeftApplication() {
                Log.d("debug","Code to be executed when the user has left the app.");
            }
            @Override
            public void onAdClosed() {
                Log.d("debug","Code to be executed when when the user is about to return to the app after tapping on an ad.");
            }
        });

        ImageButton speakbutton;
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        ImageButton button_fragment = findViewById(R.id.image_button_check);
        mLayout1 = (LinearLayout)findViewById(R.id.layout1);
        mLayout2 = (LinearLayout)findViewById(R.id.layout2);

        if(savedInstanceState == null){
            button_fragment.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();
                    //Set BackStack
                    fragmentTransaction.addToBackStack(null);
                    //Set the Parameter
                    fragmentTransaction.replace(R.id.container,
                            TestFragment.newInstance("Fragment"));
                    fragmentTransaction.commit();
                }

            });
        }
        findViewById(R.id.image_button_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout2.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.button_back_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout2.setVisibility(View.INVISIBLE);
            }
        });

        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        //image_button_speak
        ttsText = (TextView) findViewById(R.id.questionLabel);
        speakbutton = (ImageButton) findViewById(R.id.image_button_speak);
        speakbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = ttsText.getText().toString();
                if(text.length() > 0){
                    float pitch = (float) mSeekBarPitch.getProgress() / 50;
                    if (pitch < 0.1) pitch = 0.1f;
                    float speed = (float) mSeekBarSpeed.getProgress() / 50;
                    if (speed < 0.1) speed = 0.1f;
                    textToSpeech.setPitch(pitch);
                    textToSpeech.setSpeechRate(speed);
                    textToSpeech.setLanguage(new Locale("et-EE"));
                    textToSpeech.speak(text,TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        bbreset = (Button) findViewById(R.id.resetPS);
        //switchSheet = (Switch) findViewById(R.id.switchSheet);
        bbreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBarPitch.setProgress(50);
                mSeekBarSpeed.setProgress(50);
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
        }
    }

    public void onInit(int i) {
        if(i == textToSpeech.SUCCESS){
            //Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        } else if (i == textToSpeech.ERROR){
            //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void showNextQuiz() {
        //Refresh quizCount
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
    //InsertDate method
    private void insertData(SQLiteDatabase db, String com, String price){
        ContentValues values = new ContentValues();
        values.put("estonian", com);
        values.put("english", price);
        db.insert("testdbdb", null, values);
    }

    public void checkAnswer(View view) {
        //To show which button has been pushed
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();
        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "Correct!";
            rightAnswerCount++;
        } else {
            alertTitle = "Wrong...";
            if(helper == null){
                helper = new TestOpenHelper(getApplicationContext());
            }
            if(db == null){
                db = helper.getWritableDatabase();
            }
            String key = questionLabel.getText().toString();
            String value = rightAnswer;
            insertData(db, key, value);
        }

        TextView titleView = new TextView(this);
        titleView.setText(alertTitle);
        titleView.setTextSize(24);
        titleView.setTextColor(Color.WHITE);
        titleView.setBackgroundColor(getResources().getColor(R.color.alertBlue));
        titleView.setPadding(20, 20, 20, 20);
        titleView.setGravity(Gravity.CENTER);

        TextView msgView = new TextView(this);
        msgView.setText("Answer : " + rightAnswer);
        msgView.setTextSize(24);
        //msgView.setBackgroundColor(Color.RED);
        msgView.setTextColor(Color.BLACK);
        msgView.setPadding(20, 20, 40, 20);

        AlertDialog dLog = new AlertDialog.Builder(this)
                .setCustomTitle(titleView)
                .setView(msgView)
                .setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                })
                .show();
        dLog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
        dLog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.alertBlue));


        /*
        //Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(titleView);
        builder.setView(msgView);
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

         */
    }
}
