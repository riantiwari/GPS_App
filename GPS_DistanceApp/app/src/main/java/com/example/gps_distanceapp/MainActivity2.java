package com.example.gps_distanceapp;

import static com.example.gps_distanceapp.R.layout.activity_main2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity  {

    TabLayout tabLayout;
    int count = 0;
    TabLayout.Tab tab1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main2);
        tabLayout = findViewById(R.id.id_tablayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout); // get the reference of TabLayout
                int selectedTabPosition = tabLayout.getSelectedTabPosition();
                GetTabAt(selectedTabPosition);
                if(selectedTabPosition==0) {
                    openActivity1();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    void GetTabAt(int index) {

        count++;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout); // get the reference of TabLayout
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if(count>1) {
            TabLayout.Tab temp = tab;
            tab = tab1;
            tab1 = temp;
        }
    }

    public void openActivity1()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}