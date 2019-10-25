package com.example.sqldatabase_demo;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;

    EditText edittext,edittext2,edittext3,edittext4;
    Button   button,button2,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb=new DatabaseHelper(this);

        edittext=(EditText)findViewById(R.id.edittext);
        edittext2=(EditText)findViewById(R.id.edittext2);
        edittext3=(EditText)findViewById(R.id.edittext3);
        edittext4=(EditText)findViewById(R.id.edittext4);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);

        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData() {
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows=mydb.deleteData(edittext4.getText().toString());
                if (deleteRows > 0) {
                    edittext.setText(" ");
                    edittext2.setText(" ");
                    edittext3.setText(" ");
                    edittext4.setText(" ");

                    Toast.makeText(MainActivity.this, "Data Delete", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Date not datele",Toast.LENGTH_LONG).show();
                 }
            }
        });

    }

    public void UpdateData() {
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdate=mydb.updateData(edittext4.getText().toString(),edittext.getText().toString(),edittext2.getText().toString(),edittext3.getText().toString());
                if (isupdate == true) {
                    Toast.makeText(MainActivity.this, "Data is Update", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data is not Update", Toast.LENGTH_LONG).show();
                    }
                }
        });

    }
    public void AddData() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Inserted = mydb.insertDAta(edittext.getText().toString(),edittext2.getText().toString(),edittext3.getText().toString());
                if (Inserted == true)
                    Toast.makeText(MainActivity.this, "Data is Insert", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data is not Insert ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ViewAll() {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=mydb.getAllData();
                if (cursor.getCount()==0){
                    showMassage("Error","Nothing found");
                    return;
                }

                StringBuffer stringBuffer=new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("ID :"+ cursor.getString(0)+"\n");
                    stringBuffer.append("NAME :"+ cursor.getString(1)+"\n");
                    stringBuffer.append("SURNAME :"+ cursor.getString(2)+"\n");
                    stringBuffer.append("MARKS :"+ cursor.getString(3)+"\n\n");
                }
                showMassage("Data", stringBuffer.toString());

           }

        });

    }

    public void showMassage(String title, String nothing_found) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(nothing_found);
        builder.show();

    }
}