package com.example.gps_distanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class IntroductoryActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory);

        Animation topAnim, bottomAnim;
        TextView thing;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(IntroductoryActivity.this, loginPage2.class);
                startActivity(homeIntent);
                finish();
            }
        }, 1000);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim =  AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        lottieAnimationView = findViewById(R.id.lottie);
        thing = findViewById(R.id.thing);

        thing.setAnimation(bottomAnim);
        lottieAnimationView.setAnimation(topAnim);
    }
}