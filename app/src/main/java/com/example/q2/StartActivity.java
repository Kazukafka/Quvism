package com.example.q2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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
            case R.id.menu_item1:
                Intent i = new Intent(StartActivity.this,MainActivity.class);
                startActivity(i);
                return true;//this item has your app icon
            case R.id.menu_item2:
                //↓This Line is not wroking
                menuLayout.setVisibility(View.VISIBLE);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.findItem(R.id.menu_item).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
    }
}