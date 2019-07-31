package com.meysam.tasbih.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meysam.tasbih.Classes.DataBase;
import com.meysam.tasbih.Classes.ListPersonAdapter;
import com.meysam.tasbih.Classes.Person;
import com.meysam.tasbih.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    List<Person> list_person;
    ListView listView;
    ListPersonAdapter adapter;
    DataBase db;
    FloatingActionButton fb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listView = findViewById(R.id.listView_login);
        db = new DataBase(this);
        fb = findViewById(R.id.fb_login);
        bindPerson();

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                dialog.setContentView(R.layout.person_jadid_dialog);
                dialog.show();

                Button buttonEnseraf = dialog.findViewById(R.id.btn_Enseraf);
                Button buttonBlae = dialog.findViewById(R.id.btn_Taid);

                buttonEnseraf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonBlae.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText addPerson = dialog.findViewById(R.id.edt_NamePerson_jadid);
                        if (addPerson.getText().length() == 0) {
                            Toast.makeText(LoginActivity.this, "لطفا نام خود را وارد کنید", Toast.LENGTH_SHORT).show();
                        } else {
                            db.personJadid(addPerson.getText().toString());
                            db.close();
                            dialog.dismiss();
                            bindPerson();
                        }
                    }
                });
            }
        });

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("lid", list_person.get(position).id);
                intent.putExtra("lname", list_person.get(position).NamePerson);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("ویرایش");
        menu.add("حذف");
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list_person.get(info.position).getNamePerson());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "حذف") {
            final Dialog dialog = new Dialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
            dialog.setContentView(R.layout.soal_dialog);
            final Integer id = list_person.get(info.position).getId();
            TextView txt_soal = dialog.findViewById(R.id.txt_soal);
            TextView txt_header = dialog.findViewById(R.id.txt_titlesoal);
            txt_header.setText("حذف");
            txt_soal.setText("آیا مایل به حذف هستید؟");
            dialog.show();

            Button buttonEnseraf = dialog.findViewById(R.id.btn_enseraf);
            Button buttonBlae = dialog.findViewById(R.id.btn_bale);
            buttonEnseraf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buttonBlae.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deletePerson(id);
                    db.close();
                    bindPerson();
                    dialog.dismiss();
                }
            });
        } else if (item.getTitle() == "ویرایش") {
            final Dialog dialog = new Dialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
            dialog.setContentView(R.layout.virayesh_dialog);
            TextView txt_titlevirayesh = dialog.findViewById(R.id.txt_titlevirayesh);
            final TextView edt_NameDoa = dialog.findViewById(R.id.edt_NameDoa);
            TextView edt_MatnDoa = dialog.findViewById(R.id.edt_MatnDoa);
            final Integer id = list_person.get(info.position).getId();
            txt_titlevirayesh.setText("ویرایش");
            String PersonName = list_person.get(info.position).getNamePerson();
            edt_NameDoa.setText(PersonName);
            edt_MatnDoa.setVisibility(View.GONE);
            dialog.show();

            Button btn_Enseraf = dialog.findViewById(R.id.btn_Enseraf);
            btn_Enseraf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button btn_berozresani = dialog.findViewById(R.id.btn_Berozresani);
            btn_berozresani.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edt_NameDoa.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "لطفا نام را وارد نمایید", Toast.LENGTH_LONG).show();
                    } else {
                        db.updatePerson(edt_NameDoa.getText().toString(), id);
                        dialog.dismiss();
                        bindPerson();
                    }
                }
            });
        }
        return true;
    }

    public void bindPerson() {
        db = new DataBase(this);
        list_person = db.getPerson();
        adapter = new ListPersonAdapter(LoginActivity.this, list_person);
        listView.setAdapter(adapter);
    }
}
