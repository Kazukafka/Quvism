package com.example.q2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BeginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "testPackage.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        setTitle("Menu of Quvism");

        ImageButton imageButtonDaily = findViewById(R.id.image_button1);
        ImageButton imageButtonAcademic = findViewById(R.id.image_button2);
        ImageButton imageButtonBusiness = findViewById(R.id.image_button3);

        imageButtonDaily.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), QuizActivity.class);
                String str = "Daily";
                intent.putExtra(EXTRA_MESSAGE, str);
                startActivity(intent);
            }
        });
        imageButtonAcademic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), QuizActivity.class);
                String str = "Academic";
                intent.putExtra(EXTRA_MESSAGE, str);
                startActivity(intent);
            }
        });
        imageButtonBusiness.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), QuizActivity.class);
                String str = "Business";
                intent.putExtra(EXTRA_MESSAGE, str);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.info_menu:
                //↓These Lines must be under If Statement
                TextView titleView = new TextView(this);
                String alertTitle="";
                titleView.setTextSize(24);
                titleView.setTextColor(Color.WHITE);
                titleView.setPadding(20, 20, 20, 20);
                titleView.setGravity(Gravity.CENTER);
                TextView msgView = new TextView(this);
                msgView.setText("Developer Info : \nKazuhisa Noguchi\n2020/21 PR-519 Software development project");
                msgView.setTextSize(24);
                msgView.setTextColor(Color.BLACK);
                msgView.setPadding(20, 20, 40, 20);
                AlertDialog dLog = new AlertDialog.Builder(this)
                        .setView(msgView)
                        .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                dLog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                dLog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.alertB));
                return true;
            case R.id.ask_menu:
                String sharedText = "Hello, I am studying Estonian Language";
                ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this);
                builder.setText(sharedText);
                builder.setType("text/plain");
                builder.startChooser();
                return true;
            case R.id.menu_howtouse:
                Intent intent = new Intent(getApplication(), MistakesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_askq:
                String url2 = "https://forms.gle/d2YA2s8cVNGhh3vt6";
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse(url2));
                startActivity(i2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //menu.findItem(R.id.menu_item).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
    }
}