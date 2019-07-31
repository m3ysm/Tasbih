package com.meysam.tasbih.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.meysam.tasbih.R;

public class SettingActivity extends AppCompatActivity {

    TextView setting_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting_txt = findViewById(R.id.setting_txt);

        setting_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this , UserActivity.class));
                finish();
            }
        });
    }
}
