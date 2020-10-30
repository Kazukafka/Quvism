package com.example.q2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

public class StartActivity extends AppCompatActivity {
    private LinearLayout menuLayout;
    private int dbNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.R.drawable.sym_def_app_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         */

        /*
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.icon3);

         */

        ImageButton imageButton1 = findViewById(R.id.image_button1);
        ImageButton imageButton2 = findViewById(R.id.image_button2);
        ImageButton imageButton3 = findViewById(R.id.image_button3);

        /*
        Button b = findViewById(R.id.action_add_contact);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.setVisibility(View.VISIBLE);
            }
        });

         */

        /*
        findViewById(R.id.menu_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.setVisibility(View.VISIBLE);
            }
        });

         */

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                dbNum = 1;
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                dbNum = 2;
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                dbNum = 3;
                startActivity(intent);
            }
        });
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(StartActivity.this,MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.info_menu:
                String url_info = "https://www.amazon.co.jp/";
                Intent info_link = new Intent(Intent.ACTION_VIEW);
                info_link.setData(Uri.parse(url_info));
                startActivity(info_link);
                return true;
            case R.id.ask_menu:
                String url_ask = "https://www.amazon.co.uk/";
                Intent ask_link = new Intent(Intent.ACTION_VIEW);
                ask_link.setData(Uri.parse(url_ask));
                startActivity(ask_link);
                return true;
            case R.id.menu_item1:
                String url1 = "https://hatenacorp.jp/";
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(url1));
                startActivity(i1);
                return true;
                /*
                Intent i = new Intent(StartActivity.this,MainActivity.class);
                startActivity(i);
                return true;//this item has your app icon
                 */
            case R.id.menu_item2:
                String url2 = "https://techblog.yahoo.co.jp/android/androidcoordinatorlayout/";
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