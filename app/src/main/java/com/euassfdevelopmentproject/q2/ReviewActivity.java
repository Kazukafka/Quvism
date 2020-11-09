package com.euassfdevelopmentproject.q2;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ReviewActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private LinearLayout kLayout1;
    private LinearLayout kLayout2;

    int MY_DATA_CHECK_CODE = 1000;
    TextToSpeech textToSpeech;
    TextView ttsText;

    //Get Data from SQLite
    public TestOpenHelper helper;
    public SQLiteDatabase db;

    private LinearLayout laylay;

    private Timer Count;
    private TimerTask TimerTask;
    private android.os.Handler handle = new android.os.Handler();
    private TextView countTxt;
    private int count = 3;
    private int showNUM = 0;

    List<String> ee_mis = new ArrayList<>();
    List<String> eng_mis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

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

        Button btnFromReviewToBegin = findViewById(R.id.btnGOTOMENU);

        btnFromReviewToBegin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), BeginActivity.class);
            startActivity(intent);
        });

        kLayout1 = (LinearLayout)findViewById(R.id.layout1);
        kLayout2 = (LinearLayout)findViewById(R.id.layout2);

        kLayout2.setVisibility(View.INVISIBLE);

        //Generate TTS Instance
        ImageButton speakbutton;
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        ttsText = (TextView) findViewById(R.id.estonian_txt);
        speakbutton = (ImageButton) findViewById(R.id.image_button_speak_reviewAC);
        speakbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = ttsText.getText().toString();
                if(text.length() > 0){
                    float pitch = (float) 1.0;
                    float speed = (float) 1.0;
                    textToSpeech.setPitch(pitch);
                    textToSpeech.setSpeechRate(speed);
                    textToSpeech.setLanguage(new Locale("et-EE"));
                    textToSpeech.speak(text,TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        countTxt = (TextView)findViewById(R.id.countSecond_txt);
        setTitle("Recheck Your Mistakes");
        laylay = (LinearLayout)findViewById(R.id.laylay);

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

        //countSeconds();
        readData();

        TextView txtEstonian = findViewById(R.id.estonian_txt);
        TextView txtEnglish = findViewById(R.id.english_txt);
        txtEstonian.setText(ee_mis.get(showNUM));
        txtEnglish.setText("Try to Remember within 3 seconds");

        Button nextBtn = findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (showNUM==ee_mis.size()){
                    kLayout1.setVisibility(View.GONE);
                    kLayout2.setVisibility(View.VISIBLE);
                } else {
                    countSeconds();
                    txtEstonian.setText(ee_mis.get(showNUM));
                    countSeconds();
                    txtEnglish.setText("???");
                }
            }
        });
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
                    }
                }
            });
        }
    }

    public void flipAnimation(){
        final ObjectAnimator flip1 = ObjectAnimator.ofFloat(laylay, "scaleX", 1f, 0f);
        final ObjectAnimator flip2 = ObjectAnimator.ofFloat(laylay, "scaleX", 0f, 1f);
        flip1.setInterpolator(new DecelerateInterpolator());
        flip2.setInterpolator(new AccelerateDecelerateInterpolator());
        flip1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                flip2.start();
            }
        });
        flip1.start();
        TextView txtEstonian = findViewById(R.id.estonian_txt);
        TextView txtEnglish = findViewById(R.id.english_txt);
        txtEstonian.setText(ee_mis.get(showNUM));
        txtEnglish.setText(eng_mis.get(showNUM));
        showNUM++;
        count=4;
    }


    public void readData(){

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
            ee_mis.add(cur.getString(0));
            eng_mis.add(cur.getString(1));
            cur.moveToNext();
        }
        //↓Never Forget to Close Cursor
        cur.close();
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

    //Only Init Must be needed
    public void onInit(int i) {
        if(i == textToSpeech.SUCCESS){
            //Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
        } else if (i == textToSpeech.ERROR){
            //Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }
}