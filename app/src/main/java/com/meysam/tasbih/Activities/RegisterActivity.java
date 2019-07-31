package com.meysam.tasbih.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meysam.tasbih.Classes.DataBase;
import com.meysam.tasbih.Classes.Person;
import com.meysam.tasbih.R;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    List<Person> list_person;
    Button btn_register;
    EditText edt_register;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBase(this);


        btn_register = findViewById(R.id.btn_register);
        edt_register = findViewById(R.id.edt_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_register.getText().toString().length() == 0){
                    Toast.makeText(RegisterActivity.this , "لطفا نام خود را وارد کنید" , Toast.LENGTH_SHORT).show();
                }

                db.personJadid(edt_register.getText().toString());
                int id = db.getPerson().get(0).id;

                finish();
                Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                intent.putExtra("rname",edt_register.getText().toString());
                intent.putExtra("rid",id );
                startActivity(intent);
            }
        });
    }
}
