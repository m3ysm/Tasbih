package com.meysam.tasbih.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;


    public DataBase(Context context) {
        super(context, "TasbihDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE tbl_Doa (" +
                " DoaID   INTEGER PRIMARY KEY AUTOINCREMENT" +
                " NOT NULL" +
                " UNIQUE," +
                " NameDoa VARCHAR," +
                " MatnDoa VARCHAR," +
                " IDPerson INTEGER REFERENCES tbl_Person ( PersonID ) ON DELETE CASCADE " +
                " );";
        db.execSQL(query);

        query = "CREATE TABLE tbl_Person (" +
                " PersonID INTEGER PRIMARY KEY AUTOINCREMENT" +
                " NOT NULL" +
                " UNIQUE," +
                " Name     VARCHAR," +
                " Family   VARCHAR " +
                " );";
        db.execSQL(query);

        query = "CREATE TABLE tbl_DoaPerson (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                " NOT NULL" +
                " UNIQUE," +
                " IDDoa INTEGER REFERENCES tbl_Doa(DoaID) ON DELETE CASCADE," +
                " IDPerson INTEGER REFERENCES tbl_Doa ( IDPerson ) ON DELETE CASCADE ," +
                " Date DATE NOT NULL);";
        db.execSQL(query);
    }

    public Boolean personJadid(String NamePerson) {
        boolean result = true;
//        String query = "INSERT INTO tbl_Person (Name) VALUES ('" + NamePerson + "' ); SELECT last_insert_rowid()";
        String query;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL(query);
            ContentValues c = new ContentValues();
            c.put("Name", NamePerson);
            long PersonID = db.insert("tbl_Person", null, c);

            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا1' , 'متن دعا1' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا2' , 'متن دعا2' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا3' , 'متن دعا3' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا4' , 'متن دعا4' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا5' , 'متن دعا5' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا6' , 'متن دعا6' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا7' , 'متن دعا7' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا8' , 'متن دعا8' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا9' , 'متن دعا9' , " + PersonID + ")";
            db.execSQL(query);
            query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa , IDPerson) VALUES('دعا10' , 'متن دعا10' , " + PersonID + ")";
            db.execSQL(query);

            db.close();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Boolean deletePerson(Integer id) {
        boolean result;
        try {
            String query = "DELETE FROM tbl_Person WHERE PersonID=" + id;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Boolean updatePerson(String Name, Integer id) {
        boolean result = true;
        try {
            String query = "UPDATE tbl_Person SET Name = '" + Name + "' WHERE PersonID=" + id;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return result;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public List<Person> getPerson() {
        List<Person> List_Person = new ArrayList<>();
        String query = "SELECT PersonID , Name FROM tbl_Person";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Person p = new Person();
            p.id = cursor.getInt(0);
            p.NamePerson = cursor.getString(1);
            List_Person.add(p);
        }
        return List_Person;
    }

    public List<Doaha> getDoa(Integer id) {

        List<Doaha> List_Doa = new ArrayList<Doaha>();

        String query = "SELECT DoaID , NameDoa , MatnDoa , IDPerson FROM tbl_Doa WHERE IDPerson=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Doaha d = new Doaha();
            d.id = cursor.getInt(0);
            d.namedoaha = cursor.getString(1);
            d.matndoaha = cursor.getString(2);
            d.IDPerson = cursor.getInt(3);
            List_Doa.add(d);
        }
        return List_Doa;
    }

    public Boolean DoaJadid(String NameDoa, String MatnDoa, int IDPerson) {
        boolean result;
//        String query = "INSERT INTO tbl_Doa (NameDoa , MatnDoa) VALUES ('" + NameDoa + "' , '" + MatnDoa + "')";
        ContentValues cv = new ContentValues();
        cv.put("NameDoa", NameDoa);
        cv.put("MatnDoa", MatnDoa);
        cv.put("IDPerson", IDPerson);
        try {
            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL(query);
            db.insert("tbl_Doa", null, cv);
            db.close();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Boolean deleteDoa(Integer id) {
        boolean result;
        String query = "DELETE FROM tbl_Doa WHERE DoaID=" + id;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public Boolean updateDoa(String NameDoa, String MatnDoa, int ID) {
        boolean result;
        String query = "UPDATE tbl_Doa SET NameDoa ='" + NameDoa + "' , MatnDoa = '" + MatnDoa + "' WHERE DoaID =" + ID;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            db.close();
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public List<Doaha> getPersonDoa(int id) {
        List<Doaha> lsit_doaPerson = new ArrayList<>();
        String query = "SELECT DoaID , NameDoa , MatnDoa , IDPerson FROM tbl_Doa where IDPerson =" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Doaha d = new Doaha();
            d.id = cursor.getInt(0);
            d.namedoaha = cursor.getString(1);
            d.matndoaha = cursor.getString(2);
            d.IDPerson = cursor.getInt(3);
//            d.namedoaha = cursor.getString(0);
//            d.matndoaha = cursor.getString(1);
//            d.IDPerson = cursor.getInt(2);
//            d.IDDoa = cursor.getInt(3);

            lsit_doaPerson.add(d);
        }
        return lsit_doaPerson;
    }

    public Boolean addDoa(Integer iddoa ,Integer idperson , String date) {
        boolean result;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("IDDoa", iddoa);
            cv.put("IDPerson", idperson);
            cv.put("Date", date);
            db.insert("tbl_DoaPerson", null, cv);
            db.close();
            result =true;
        }catch (Exception e){
            result=false;
        }
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_Doa");
        onCreate(db);
    }


}
