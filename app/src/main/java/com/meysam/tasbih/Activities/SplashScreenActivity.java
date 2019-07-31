package com.meysam.tasbih.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.meysam.tasbih.Classes.DataBase;
import com.meysam.tasbih.R;

public class SplashScreenActivity extends AppCompatActivity implements Animation.AnimationListener {

    ImageView img_splash;
    TextView txt_splash;
    Animation animation1;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        db = new DataBase(this);

        img_splash = findViewById(R.id.img_splash);
        txt_splash = findViewById(R.id.txt_splash);
        animation1 = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.anim.fade_in);
        img_splash.startAnimation(animation1);
        txt_splash.startAnimation(animation1);
        animation1.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        IsEmpty();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    public void IsEmpty(){
        db = new DataBase(this);
        if (db.getPerson().isEmpty()){
            startActivity(new Intent(SplashScreenActivity.this , RegisterActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashScreenActivity.this , UserActivity.class));
            finish();
        }
    }
}
