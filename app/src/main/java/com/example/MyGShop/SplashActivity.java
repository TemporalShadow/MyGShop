package com.example.MyGShop;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.proyectonavigationbar.R;

public class SplashActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(intent);
        finish(); // evitar regresar a éste activity
    }*/
    private static int SPLASH_TIME_OUT = 5000;
    //Hooks
    //TextView a, slogan;
    ImageView image;
    TextView name;
    //Animations
    Animation topAnimantion,bottomAnimation,middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashscreen);

        //Hooks
        name= findViewById(R.id.appname);
        image = findViewById(R.id.logo);

        //Animation Calls
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //-----------Setting Animations to the elements of Splash
        image.setAnimation(middleAnimation);
        name.setAnimation(bottomAnimation);

        //Splash Screen Code to call new Activity after some time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Calling new Activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}