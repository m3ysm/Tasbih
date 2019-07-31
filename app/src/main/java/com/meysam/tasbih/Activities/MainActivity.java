package com.meysam.tasbih.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.meysam.tasbih.Classes.DataBase;
import com.meysam.tasbih.Classes.Doaha;
import com.meysam.tasbih.Classes.SolarCalendar;
import com.meysam.tasbih.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView listViewDoaha;
    ArrayAdapter<String> listAdapter;
    List<Doaha> list_Doaha;
    private DataBase db;
    TextView txt_DoaRoz, txt_roz, txt_tarikh , txt_personname , txt_onvan;
    ImageButton  img_chart, img_setting , img_home;
    Integer PersonID;
    Boolean doubleTab = false;
    Toast backToast;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBase(this);
        txt_personname = findViewById(R.id.txt_personname);
        txt_onvan = findViewById(R.id.txt_onvan);
        txt_DoaRoz = findViewById(R.id.txt_DoaRoz);
        listViewDoaha = findViewById(R.id.listViewDoaha);
        txt_roz = findViewById(R.id.txt_roz);
        txt_tarikh = findViewById(R.id.txt_tarikh);
        img_chart = findViewById(R.id.img_chart);
        img_setting = findViewById(R.id.img_setting);
//        img_home = findViewById(R.id.img_home);
        fb = findViewById(R.id.main_fab);

        bindDoa1();

        Locale loc = new Locale("en_US");
        SolarCalendar sc = new SolarCalendar();
        String TarikhEmroz = String.valueOf(sc.year) + "/"
                + String.format(loc, "%02d", sc.month) + "/"
                + String.format(loc, "%02d", sc.date);
        txt_tarikh.setText(TarikhEmroz);
        txt_roz.setText(sc.strWeekDay);

        switch (sc.strWeekDay) {
            case "شنبه":
                txt_DoaRoz.setText("یا رَبَّ الْعالَمین");
                break;
            case "یکشنبه":
                txt_DoaRoz.setText("یا ذَالْجَلالِ وَالْاِکْرام");
                break;
            case "دو شنبه":
                txt_DoaRoz.setText("یا قاضِیَ الْحاجات");
                break;
            case "سه شنبه":
                txt_DoaRoz.setText("یا اَرْحَمَ الرّاحِمین");
                break;
            case "چهارشنبه":
                txt_DoaRoz.setText("یا حَیُّ یا قَیّوم");
                break;
            case "پنجشنبه":
                txt_DoaRoz.setText("لا اِلهَ اِلّا اللهُ الْمَلِکُ الْحَقُّ الْمُبین");
                break;
            case "جمعه":
                txt_DoaRoz.setText("اَللّهُمَّ صَلِّ عَلی مُحَمَّدٍ وَ آلِ مُحَمَّدٍ وَ عَجِّلْ فَرَجَهُمْ");
                break;
        }

        registerForContextMenu(listViewDoaha);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                dialog.setContentView(R.layout.jadid_dialog);
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

                        EditText NameDoaJadid = dialog.findViewById(R.id.edt_NameDoa_jadid);
                        EditText MatnDoaJadid = dialog.findViewById(R.id.edt_MatnDoa_jadid);

                        if (NameDoaJadid.getText().length() == 0) {
                            Toast.makeText(MainActivity.this, "لطفا نام دعا را وارد کنید", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (MatnDoaJadid.getText().length() == 0) {
                            Toast.makeText(MainActivity.this, "لطفا متن دعا را وارد کنید", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            int tedad = 0;
                            db.DoaJadid(NameDoaJadid.getText().toString(), MatnDoaJadid.getText().toString() ,getPersonID());
                            dialog.dismiss();
                            bindDoa1();
                        }
                    }
                });
            }
        });

        img_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NemoudarActivity.class);
                startActivity(intent);
            }
        });

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , SettingActivity.class));
            }
        });

//        img_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,CounterActivity.class));
//            }
//        });


        listViewDoaha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , CounterActivity.class);
                intent.putExtra("namedoa",list_Doaha.get(position).namedoaha);
                intent.putExtra("matndoa",list_Doaha.get(position).matndoaha);
                intent.putExtra("iddoa",list_Doaha.get(position).id);
                intent.putExtra("idperson",list_Doaha.get(position).IDPerson);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("ویرایش");
        menu.add("حذف");

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list_Doaha.get(info.position).namedoaha);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "حذف") {
            final Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
            dialog.setContentView(R.layout.soal_dialog);

            TextView TextViewSola = dialog.findViewById(R.id.txt_soal);
            TextView TextViewTitle = dialog.findViewById(R.id.txt_titlesoal);
            final Integer id = list_Doaha.get(info.position).id;
            TextViewTitle.setText("حذف ");
            TextViewSola.setText("آیا مایل به حذف هستید؟");

            dialog.show();

            Button buttonEnseraf = dialog.findViewById(R.id.btn_enseraf);
            Button buttonBlae = dialog.findViewById(R.id.btn_bale);

            buttonBlae.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteDoa(id);
                    dialog.dismiss();
                    bindDoa1();
                }
            });

            buttonEnseraf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else if (item.getTitle() == "ویرایش") {
            final Dialog dialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
            dialog.setContentView(R.layout.virayesh_dialog);
            TextView TextVirayeshTitle = dialog.findViewById(R.id.txt_titlevirayesh);
             final EditText editTextNameDoa = dialog.findViewById(R.id.edt_NameDoa);
              final EditText editTextMatneDoa = dialog.findViewById(R.id.edt_MatnDoa);
            final Integer ID = list_Doaha.get(info.position).getId();
            String NameDoa = list_Doaha.get(info.position).namedoaha;
            String MatnDoa = list_Doaha.get(info.position).matndoaha;
            TextVirayeshTitle.setText("ویرایش");
            editTextNameDoa.setText(NameDoa);
            editTextMatneDoa.setText(MatnDoa);
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
                    if (editTextNameDoa.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "لطفا نام دعا و متن دعا را وارد نمایید", Toast.LENGTH_LONG).show();
                    } else {
                        db.updateDoa(editTextNameDoa.getText().toString() , editTextMatneDoa.getText().toString() , ID);
                        dialog.dismiss();
                        bindDoa1();
                    }
                }
            });
        }
        return true;
    }

    public void bindDoa() {

        list_Doaha.clear();
        Integer id = db.getPerson().get(0).id;
        list_Doaha = db.getDoa(id);
        ArrayList<String> list = new ArrayList<>();
        for (int i=0 ; i<list_Doaha.size() ; i++){
            list.add(list_Doaha.get(i).namedoaha);
        }

        listAdapter = new ArrayAdapter<String>(this , R.layout.doa_row , R.id.txt_name_doa_row , list);
        listViewDoaha.setAdapter(listAdapter);

    }

    public void bindDoa1(){

//        Intent intent = getIntent();
//        Integer df = null;
//        Integer rid = intent.getIntExtra("rid",df);
//        Integer lid = intent.getIntExtra("lid",df);

//        if (rid == null){
//            list_Doaha = db.getPersonDoa(lid);
//        }else if (lid == null){
//            list_Doaha = db.getPersonDoa(rid);
//        }
        getPersonID();
        getName();
        getList_Doaha();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0 ; i<list_Doaha.size() ; i++){
            list.add(list_Doaha.get(i).namedoaha);
        }
        listAdapter = new ArrayAdapter<String>(this , R.layout.doa_row , R.id.txt_name_doa_row , list);
        listViewDoaha.setAdapter(listAdapter);
    }

    public TextView getName(){
        Intent intent = getIntent();
        String lname = intent.getStringExtra("lname");
        String rname = intent.getStringExtra("rname");
        String uname = intent.getStringExtra("uname");
        if (lname == null && uname == null){
            txt_personname.setText(rname);
        }else if (rname == null && uname == null){
            txt_personname.setText(lname);
        }else {
            txt_personname.setText(uname);
        }
        return txt_personname;
    }

    public List<Doaha> getList_Doaha(){
        Intent intent = getIntent();
        Integer rid = intent.getIntExtra("rid",-1);
        Integer lid = intent.getIntExtra("lid",-1);
        Integer uid = intent.getIntExtra("uid",-1);

        if (rid == -1 && uid == -1){
            list_Doaha = db.getPersonDoa(lid);
        }else if (lid == -1 && uid == -1){
            list_Doaha = db.getPersonDoa(rid);
        }else {
            list_Doaha = db.getPersonDoa(uid);
        }
        return list_Doaha;
    }

    public Integer getPersonID(){

        Intent intent = getIntent();
        Integer rid =intent.getIntExtra("rid", -1);
        Integer lid = intent.getIntExtra("lid",-1);
        Integer uid = intent.getIntExtra("uid",-1);
        if (rid == -1 && uid == -1){
            Integer id = lid;
            PersonID = id;
        }else if (lid == -1 && uid == -1){
            Integer id = rid;
            PersonID=id;
        }else {
            Integer id = uid;
            PersonID=id;
        }
        return PersonID ;
    }

    @Override
    public void onBackPressed() {
        if (doubleTab){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        this.doubleTab = true;
        backToast = Toast.makeText(this , "لطفا برای خروج دوباره لمس کنید" , Toast.LENGTH_SHORT);
        backToast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTab = false;
            }
        },2000);
    }
}
