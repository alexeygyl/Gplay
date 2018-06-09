package com.example.smit.gplay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().hide();
            findAllViews();
            navigation.setOnNavigationItemSelectedListener(this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    void findAllViews(){
        mTextMessage = findViewById(R.id.message);
        navigation =  findViewById(R.id.navigation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_music:
                mTextMessage.setText(R.string.Music);
                return true;
            case R.id.navigation_radio:
                mTextMessage.setText(R.string.Radio);
                return true;
            case R.id.navigation_online:
                mTextMessage.setText(R.string.Online);
                return true;
//            case R.id.navigation_configuration:
//                mTextMessage.setText(R.string.Configuration);
//                return true;
        }
        return false;
    }
}
