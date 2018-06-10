package com.example.smit.gplay;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Communicator{
    public static String LOGTAG="LOG";

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MusicFragment musicFragment;
    private RadioFragment radioFragment;
    private OnlineFragment onlineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().hide();
            findAllViews();
            navigation.setOnNavigationItemSelectedListener(this);
            musicFragment = new MusicFragment(this);
            radioFragment = new RadioFragment(this);
            onlineFragment = new OnlineFragment(this);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.FragmentBody,musicFragment);
            fragmentTransaction.commit();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    void findAllViews(){
        navigation =  findViewById(R.id.navigation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_music:
                SetFragment(R.id.FragmentBody,musicFragment);
                return true;
            case R.id.navigation_radio:
                SetFragment(R.id.FragmentBody,radioFragment);
                return true;
            case R.id.navigation_online:
                SetFragment(R.id.FragmentBody,onlineFragment);
                return true;
        }
        return false;
    }


    void SetFragment(int layout, Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMusicListUpdate(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFolderAdd(int pos) {
        fragmentManager = getSupportFragmentManager();
        MusicFragment fragment = (MusicFragment) fragmentManager.findFragmentById(R.id.FragmentBody);
        fragment.onFolderAdd(pos);
    }

    @Override
    public void onFolderDel(int pos) {
        fragmentManager = getSupportFragmentManager();
        MusicFragment fragment = (MusicFragment) fragmentManager.findFragmentById(R.id.FragmentBody);
        fragment.onFolderDel(pos);
    }
}
