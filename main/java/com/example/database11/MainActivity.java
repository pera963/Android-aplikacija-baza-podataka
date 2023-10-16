package com.example.database11;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase;
    private EditText editTextName, editTextSecondName, editTextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openOrCreateDatabase();
        initializeEditText();
        //  insertDate();
        //  getDateFromDatebase();
        getPeopleOlderThan(10);
    }
   private void initializeEditText()
   {
       editTextName =(EditText)findViewById(R.id.editTexName);
       editTextSecondName =(EditText)findViewById(R.id.editTextSecondName);
       editTextAge =(EditText)findViewById(R.id.editTexAgeName);

   }




    private void openOrCreateDatabase() {
        sqLiteDatabase = openOrCreateDatabase("People", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS  human(ID INTEGER PRIMARY KEY, name VARCHAR,secondname VARCHAR" +
                ", age INTEGER  )");

    }

    // ubacivanje podataka u bazu
    private void insertDate() {
        sqLiteDatabase.execSQL("INSERT INTO human(name,secondname, age)VALUES('Ivan','Ivic',20)");
        sqLiteDatabase.execSQL("INSERT INTO human(name,secondname, age)VALUES('Ante','Ivic',40)");
        sqLiteDatabase.execSQL("INSERT INTO human(name,secondname, age)VALUES('Ante','Peric',15)");
    }

    private void getDateFromDatebase() {
        //Cursor je veza između nasi baze podataka
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM human", null);

        int idIndex = cursor.getColumnIndex("ID");
        int nameIndex = cursor.getColumnIndex("name");
        int secondIndex = cursor.getColumnIndex("secondname");
        int ageIndex = cursor.getColumnIndex("age");

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();//ako je sve u redu ,cursor ide na početak

            //ispisivanje indexa kolona 0,1,2,3, pretvaranje int u String

            Log.i("INFO123", "Indexes :" + Integer.toString(idIndex) + " " + Integer.toString(nameIndex) + " " +
                    Integer.toString(secondIndex) + " " + Integer.toString(ageIndex));


            do {
                Log.i("INFO123", Integer.toString(cursor.getInt(idIndex)) + " " + cursor.getString(nameIndex)
                        + " " + cursor.getString(secondIndex) + " " + Integer.toString(cursor.getInt(ageIndex)));
            }
            while (cursor.moveToNext());
            //kad vidi da nema više u bazi podataka on staje
        }

        //" AND  name = ' Ivan ' "
    }

    private void getPeopleOlderThan(int years) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM human WHERE age >  " + years + " AND  name = 'Ivan' ", null);

        int idIndex = cursor.getColumnIndex("ID");
        int nameIndex = cursor.getColumnIndex("name");
        int secondIndex = cursor.getColumnIndex("secondname");
        int ageIndex = cursor.getColumnIndex("age");

        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();//ako je sve u redu ,cursor ide na početak

            //ispisivanje indexa kolona 0,1,2,3, pretvaranje int u String

            Log.i("INFO123", "Indexes :" + Integer.toString(idIndex) + " " + Integer.toString(nameIndex) + " " +
                    Integer.toString(secondIndex) + " " + Integer.toString(ageIndex));


            do {
                Log.i("INFO123", Integer.toString(cursor.getInt(idIndex)) + " " + cursor.getString(nameIndex)
                        + " " + cursor.getString(secondIndex) + " " + Integer.toString(cursor.getInt(ageIndex)));
            }
            while (cursor.moveToNext());
            //kad vidi da nema više u bazi podataka on staje
        }

    }

    public void clickinsertButton(View view)
    {
        String name=editTextName.getText().toString();
        String secondName=editTextSecondName.getText().toString();
        int age=Integer.valueOf(editTextAge.getText().toString());
    }
    private void inserValuesIntoDatabase(String name,String secondName,int age )
    {
        String sqlStatement=" INSERT INTO human(name,secondname,age)VALUES(?,?,?)";
        SQLiteStatement statement=sqLiteDatabase.compileStatement(sqlStatement);

        statement.bindString(1,name);
        statement.bindString(2,secondName);
        statement.bindLong(3,age);

        statement.execute();
    }

}