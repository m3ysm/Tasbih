package com.meysam.tasbih.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.meysam.tasbih.Classes.DataBase;
import com.meysam.tasbih.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CounterActivity extends AppCompatActivity {

    Button btn, btn_r;
    TextView txt_count, matnDoa_counter;
    ImageView img_count;
    int count = 0;
    DataBase db;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);


        db = new DataBase(CounterActivity.this);

        Intent intent = getIntent();
        String namedoa = intent.getStringExtra("namedoa");
        String matndoa = intent.getStringExtra("matndoa");
        final int iddoa = intent.getIntExtra("iddoa", -1);
        final int idperson = intent.getIntExtra("idperson", -1);

        final String date = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH).format(new Date());

        matnDoa_counter = findViewById(R.id.matnDoa_counter);
        txt_count = findViewById(R.id.count_txt);
        img_count = findViewById(R.id.img_count);
        animation = AnimationUtils.loadAnimation(this,R.anim.repeat);
        img_count.startAnimation(animation);

        matnDoa_counter.setText(matndoa);


        btn = findViewById(R.id.count_btn);
        btn_r = findViewById(R.id.count_btn_r);
        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                txt_count.setText(String.valueOf(count));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Landing).playOn(txt_count);
                txt_count.setText(String.valueOf(count = count + 1));
                db.addDoa(iddoa ,idperson,date);
            }
        });
    }
}
