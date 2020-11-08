package com.example.q2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    public static final String EXTRA_MESSAGE2 = "testPackage2.MESSAGE";

    public String message;

    private int num_outcome_define = 0;

    //SQLite Components
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    private LinearLayout kLayout1;
    private LinearLayout kLayout2;
    private LinearLayout kLayout3;

    //Switch switchSheet = (Switch) findViewById(R.id.switchSheet);
    int MY_DATA_CHECK_CODE = 1000;
    TextToSpeech textToSpeech;
    private TextView countLabel;
    private TextView questionLabel;
    private String hintQuestionLavel;
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
    private Switch switchSheet;

    //Fragment Data Moveing 1) Create SP in Main 2) Call the sp in Fragmet 3) Edit sp in fragment 4) get data from SP in Main
    ArrayList<ArrayList<String>> questionArray1 = new ArrayList<>();

    //Test by SharedPriffferences
    String[][] qDB1 = {
            // {"Estonian", "RightAnswer", "Choice_1", "Choice _２", "Choice_３"}
            {"Tere", "Hello", "Thanks", "Oh my god", "No"},
            {"Aitah", "Thank you", "Yes", "Well Done", "Not really"},
            {"Homikst", "Morning","ABC", "Doremi", "Evening"},
            {"Head ööd!", "Good Evening", "How are you?", "Fine", "Soso"},
            {"Nägemist!", "Bye!","See you", "Good", "Never"},
            {"Maga hästi!", "Sleep well","Hey", "Never mind", "right"},
            {"Homseni!", "See you tomorrow", "ABC", "fine", "Soso"},
            {"Head ööd!", "Good night", "No", "Yes", "again"},
            {"Tere tulemast!", "Welcome! (informal)", "What's your name?", "Pleased to meet you", "It is easy"},
            {"Tänan, hästi. Ja teil?", "Reply to 'How are you?", "Do you understand?", "Do you speak English?", "Don't you get it?"},
            {"Pole ammu näinud", "long time no see", "enough to do", "see you", "Not really"},
            {"Head aega", "Good bye","Good sleep", "see you", "Reply to thank you"},
            {"Meeldiv tutvuda", "Pleased to meet you", "How are you?", "Good evening", "well done"},
            {"Jah, natuke räägin", "Yes, a little","See you", "Good afternoon", "Never mind"},
            {"Kas sa saaksid seda korrata?", "Please say that again", "Please write it down", "go ahead", "I don't know"},
            {"Ma ei tea", "I don't know", "No idea", "Do you speak English?", "I understand"},
            {"Vabandust", "Sorry", "all right", "what", "text"},
            {"Kuidas Sa ütled ... eesti keeles?", "How do you say ... in Estonian?", "How much is this?", "Yes, a little", "I don't know"},
            {"Vabandage!", "Excuse me", "pardon me", "How do you do?", "Please write it down"},
            {"Tänan teid väga", "Thank you", "Excuse me", "all right", "I understand"},
            {"Tänan teid", "Thank you", "Excuse me", "all right", "I understand"},
            {"Võta heaks", "Reply to thank you", "Reply to sorry", "Reply to morning", "Welcome! (informal)"},
            {"See härra maksab kõige eest", "Excuse me", "Please say that again", "Good afternoon", "Never mind"},
            {"Kus asub tualettruum?", "Where's the toilet / bathroom?", "Reply to thank you", "fine", "It is easy"},
            {"Kus on tualett?", "Where's the toilet / bathroom?", "How do you say ... in Estonian?", "Reply to thank you", "Yes, a little"},
            {"Ma igatsen sind", "I miss you", "I love you", "I like you", "It is easy"},
            {"Ma armastan sind", "I love you", "I miss you", "I like you", "It is easy"},
            {"Saa ruttu terveks!", "Get well soon", "Take care", "fine", "Ready to go"},
            {"Jätke mind rahule!", "Go away!", "believe me", "trust me", "I like you"},
            {"Jätke mind rahule!", "Leave me alone!", "Good bye","Good sleep", "see you"},
            {"Appi!", "Help!", "touch", "head", "put"},
            {"Tulekahju!", "fire", "help", "hey", "stop"},
            {"Pidage kinni!", "stop", "help", "correct", "see you"},
            {"Kutsuge politsei!", "Call the police!", "help me out", "stop", "fire"},
            {"Häid jõule", "Christmas greetings", "Call the police!", "help", "touch"},
            {"Head uut aastat", "New Year greetings", "Christmas greetings", "Call the police!", "help"},
            {"Palju õnne sünnipäevaks", "Birthday greetings", "Birthday greetings", "Easter greetings", "see you"},
            {"Häid lihavõttepühi", "Easter greetings", "Call the police!", "Birthday greetings", "Birthday greetings"},
            {"Ühest keelest ei piisa kunagi", "One language is never enough", "Only English", "Birthday greetings", "Christmas greetings"},
            {"Mu hõljuk on angerjaid täis", "My hovercraft is full of eels", "Get well soon", "Good bye","Good sleep"},

    };

    String[][] qDB2 = {
            // {"Estonian", "RightAnswer", "Choice_1", "Choice _２", "Choice_３"}
            {"Esimene", "first","second", "great", "fine"},
            {"alla", "down","up", "left", "right"},
            {"kes", "who", "whose", "yours", "done"},
            {"võib", "may", "probably", "voice", "asap"},
            {"pool", "side", "Hell", "Yes", "It is easy"},
            {"olnud", "been", "Thanks", "Oh my god", "No"},
            {"nüüd", "now", "strong", "seriously", "Not really"},
            {"leida", "find","check", "know", "now"},
            {"juht", "head", "How are you?", "learn", "copy"},
            {"seisma", "stand","take", "walk", "stop"},
            {"enda", "own","should", "Never mind", "wrong"},
            {"lehekülg", "page", "win", "eye", "barrier"},
            {"peaks", "should", "oh god", "been", "text"},
            {"riik", "country", "lose", "test", "famous"},
            {"leitud", "found", "answer", "tall", "high"},
            {"uuring", "study", "crete", "flamboyant", "cool"},
            {"veel", "still", "destroy", "beautiful", "vote"},
            {"õppida", "learn", "run", "walk", "swim"},
            {"tehase", "plant", "use", "item", "operation"},
            {"kate", "cover", "over", "judge", "lean"},
            {"toidu", "food", "sugar", "drink", "very"},
            {"päike", "sun", "mars", "moon", "sky"},
            {"neli", "four", "five", "east", "quit"},
            {"vahel", "between", "while", "heaven", "food"},
            {"riik", "state", "speak", "whip", "water"},
            {"hoida", "keep", "mean", "nest", "cover"},
            {"silma", "eye", "ear", "mouse", "hand"},
            {"kunagi", "never", "maybe", "probably", "50%"},
            {"viimane", "last", "next", "previous", "sure"},
            {"lase", "let", "they", "yours", "mine"},
            {"arvasin", "thought", "guess", "image", "past"},
            {"linna", "city", "town", "nation", "farm"},
            {"puu", "tree", "leaf", "flour", "bee"},
            {"rist", "cross", "temple", "farm", "apartment"},
            {"raske", "hard", "easy", "middle", "short"},
            {"algus", "start", "end", "finish", "go"},
            {"võiks", "might", "really", "tell", "camera"},
            {"lugu", "story", "novel", "book", "read"},
            {"saag", "saw", "see", "watch", "watched"},
            {"meri", "sea", "land", "ocean", "forest"},
    };

    String[][] qDB3 = {
            // {"Estonian", "RightAnswer", "Choice_1", "Choice _２", "Choice_３"}
            {"avatud", "open", "close", "half", "full"},
            {"kõndida", "walk", "sit", "have", "talk"},
            {"Näiteks", "Example", "testify", "check", "forest"},
            {"leevendada", "ease", "marry", "ocean", "forest"},
            {"rühm", "group", "item", "team", "school"},
            {"alati", "always", "someday", "anymore", "correct"},
            {"muusika", "music", "art", "drawing", "paper"},
            {"need", "those", "these", "opera", "fire"},
            {"märgi", "mark", "fact", "card", "tax"},
            {"sageli", "often", "absolutely", "animation", "few"},
            {"kiri", "letter", "italy", "paper", "correction"},
            {"teine", "second", "third", "forth", "hegemony"},
            {"hooldus", "care", "show", "earn", "reset"},
            {"tavaline", "plain", "normal", "head", "best"},
            {"tüdruk", "girl", "boy", "lady", "child"},
            {"tavaline", "usual", "zeal", "ostentatiously", "rocket"},
            {"noored", "young", "childish", "simple", "elder"},
            {"valmis", "tready", "rain", "former", "go"},
            {"eespool", "above", "below", "elegant", "waterfall"},
            {"kunagi", "ever", "forever", "recorrect", "lack"},
            {"punane", "red", "blue", "pink", "black"},
            {"nimekiri", "list", "menu", "agree", "line"},
            {"kuigi", "though", "even", "odd", "prime"},
            {"tunda", "feel", "free", "mind", "brainwash"},
            {"jutt", "talk", "say", "run", "ease"},
            {"lind", "bird", "lion", "tiger", "wolf"},
            {"varsti", "soon", "later", "feed", "dig"},
            {"keha", "body", "foot", "brain", "arm"},
            {"koer", "dog", "cat", "elf", "mirror"},
            {"otsene", "direct", "straight", "opposite", "quiet"},
            {"kujutavad", "pose", "attend", "please", "set"},
            {"lahkuma", "leave", "feel", "deep", "act"},
            {"must", "black", "keen", "help", "heal"},
            {"numbritega", "numeral", "prime", "orange", "blueberry"},
            {"lühike", "short", "long", "make", "cut"},
            {"klass", "class", "team", "colleague", "roommate"},
            {"küsimus", "question", "ask", "trigger", "save"},
            {"juhtuma", "happen", "easy", "cake", "omen"},
            {"täielik", "complete", "defend", "offend", "step"},
            {"laev", "ship", "yat", "ferry", "cruiser"},
            {"piirkond", "area", "space", "road", "lake"},
            {"poole", "half", "middle", "lead", "long"},
            {"selleks", "order", "initialize", "emulate", "generate"},
            {"tulekahju", "fire", "ghost", "parka", "operate"},
            {"kogu", "whole", "geek", "engineer", "user"},
            {"korrutada", "multiply", "subtraction", "plus", "divide"},
            {"midagi", "nothing", "element", "pocket", "inch"},
            {"jääda", "stay", "multiply", "monster", "inch"},
            {"tolli", "inch", "result", "hand", "peak"},
            {"rool", "wheel", "invest", "site", "copy"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setTitle("Quiz");
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
        kLayout1 = (LinearLayout)findViewById(R.id.layout1);
        kLayout2 = (LinearLayout)findViewById(R.id.layout2);
        kLayout3 = (LinearLayout)findViewById(R.id.layout3);

        findViewById(R.id.image_button_check).setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    questionLabel.setText(rightAnswer);
                    questionLabel.setTextColor(Color.RED);
                    break;
                case MotionEvent.ACTION_UP:
                    questionLabel.setText(hintQuestionLavel);
                    questionLabel.setTextColor(Color.GRAY);
                    break;
            }
            return false;
        });

        findViewById(R.id.image_button_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kLayout2.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.button_back_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kLayout2.setVisibility(View.INVISIBLE);
            }
        });

        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        switchSheet = (Switch) findViewById(R.id.switchSheet);
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
        //Switch switchSheet = (Switch) findViewById(R.id.switchSheet);
        bbreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBarPitch.setProgress(50);
                mSeekBarSpeed.setProgress(50);
                switchSheet.setChecked(false);
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
        Intent intent1 = getIntent();
        message = intent1.getStringExtra(BeginActivity.EXTRA_MESSAGE);
        if (message.equals("Academic")){
            for (String[] quizDatum : qDB2) {
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
                num_outcome_define=0;
            }
            showNextQuiz();
        } else if (message.equals("Business")){
            //↓Here works
            for (String[] quizDatum : qDB3) {
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
                num_outcome_define=1;
            }
            showNextQuiz();
        } else {
            //↓Here works
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
                num_outcome_define=2;
            }
            showNextQuiz();
        }
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

    public int hardModeNum = 1;
    public void hardMode(){
        CompoundButton toggle = (CompoundButton) findViewById(R.id.switchSheet);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    kLayout3.setVisibility(View.VISIBLE);
                    hardModeNum = 2;
                } else {
                    kLayout3.setVisibility(View.INVISIBLE);
                    hardModeNum = 1;
                }
            }
        });

        if (hardModeNum == 2){
            kLayout3.setVisibility(View.VISIBLE);
        } else {
            kLayout3.setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.redsh1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kLayout3.setVisibility(View.INVISIBLE);
            }
        });
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
        hintQuestionLavel = questionLabel.getText().toString();
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
        hardMode();
    }
    //InsertDate method
    private void insertData(SQLiteDatabase db, String com, String price){
        ContentValues values = new ContentValues();
        values.put("estonian", com);
        values.put("english", price);
        db.insert("mistakesDB", null, values);
    }

    public void checkAnswer(View view) {
        //To show which button has been pushed
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();
        String alertTitle="";
        TextView titleView = new TextView(this);

        if (btnText.equals(rightAnswer)) {
            alertTitle = "Correct!";
            titleView.setBackgroundColor(getResources().getColor(R.color.alertGreen));
            rightAnswerCount++;
        } else {
            alertTitle = "Wrong...";
            titleView.setBackgroundColor(getResources().getColor(R.color.alertRed));
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

        //↓These Lines must be under If Statement
        titleView.setText(alertTitle);
        titleView.setTextSize(24);
        titleView.setTextColor(Color.WHITE);
        titleView.setPadding(20, 20, 20, 20);
        titleView.setGravity(Gravity.CENTER);

        TextView msgView = new TextView(this);
        msgView.setText("Answer : " + rightAnswer);
        msgView.setTextSize(24);
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
                            Intent intent = new Intent(getApplicationContext(), OutcomeActivity.class);
                            intent.putExtra("Count_right_ans", rightAnswerCount);
                            intent.putExtra("againQuiz", num_outcome_define);
                            startActivity(intent);

                        } else {
                            qCount++;
                            showNextQuiz();
                        }
                    }
                })
                .show();
        dLog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
        dLog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.alertB));
    }
}